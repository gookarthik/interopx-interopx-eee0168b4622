import { Component, Input, OnInit, ViewChild } from '@angular/core';
import { MatDialog, MatTableDataSource, MatColumnDef, MatPaginator, MatSort } from '@angular/material';
import { UtilityService } from '../shared/utility.service';
import { grpMgmt_DATA } from '../shared/entities/mock-data';
import { EditGrpComponent } from './editgrpmanagement/editgrpmanagement.component';
import { AddMemebersToGroup } from './addmemberstogrp/addmemberstogrp.component';
import { GrpmanagementService } from './grpmanagement.service';


@Component({
    selector: 'app-group',
    templateUrl: './grpmanagement.component.html',
    styleUrls: ['./grpmanagement.component.scss'],
})
export class GrpManagementComponent implements OnInit {
    grouplist: any;
    responsebody: any;
    userIdlist: any = [];
    displayedColumns = ['select', 'grpname', 'grpdesc', 'createdUname'];
    dataSource = new MatTableDataSource();
    @ViewChild(MatPaginator) matpaginator: MatPaginator;
    @ViewChild(MatSort) sort: MatSort;

    constructor(private dialog: MatDialog, private grpmgmtservice: GrpmanagementService, private util: UtilityService) { }

    ngOnInit() {
        this.loadgroups();
    }
    applyFilter(filterValue: string) {
        this.dataSource.filter = filterValue.trim().toLowerCase();
    }
    editgroup() {
        const title = 'Edit';
        const dialogRef = this.dialog.open(EditGrpComponent, {
            width: '600px',
            data: { title }
        });
    }

    createNewGroup() {
        const title = 'Add';
        const dialogRef = this.dialog.open(EditGrpComponent, {
            width: '600px',
            data: { title }
        });
        dialogRef.afterClosed().subscribe(result => {
            if (result !== '' && result !== undefined) {
                const json = {
                    groupName: result.GroupName,
                    groupDesc: result.GroupDesc
                }
                this.grpmgmtservice.creategroup(json).subscribe(res => {
                    this.loadgroups();
                    this.util.notify(
                        'Group created Successfully',
                        'success',
                        'Success'
                    );
                })
            }
        })
    }

    addmembers(row) {
        const dialogRef = this.dialog.open(AddMemebersToGroup, {
            width: '600px'
        });
        dialogRef.afterClosed().subscribe(result => {
            if (result !== '' && result !== undefined) {
                this.userIdlist = [];
                for (let i = 0; i < result.length; i++) {
                    const id = {
                        userId: String(result[i].id)
                    }
                    this.userIdlist.push(id);

                }
                this.grpmgmtservice.assignUsersToGrp(JSON.stringify(this.userIdlist), row.grpid).subscribe(res => {
                    this.util.notify(
                        'Added Users to Group Successfully',
                        'success',
                        'Success'
                    );

                });

            }
        });
    }

    deactivategroup(ev) {
        const id = ev.grpid;
        this.grpmgmtservice.deactivategroup(id).subscribe(res => {
            this.loadgroups();
            this.util.notify(
                'Group deactivated Successfully',
                'success',
                'Success'
            );
        })
    }

    loadgroups() {
        this.grpmgmtservice.getListofAllGroups().subscribe((res: Response) => {
            this.responsebody = res.body;
            const result = this.responsebody;
            this.grouplist = [];
            for (let i = 0; i < result.length; i++) {
                const groupObject = {
                    grpname: this.responsebody[i].groupName,
                    grpdesc: this.responsebody[i].groupDesc,
                    createdUname: this.responsebody[i].createdUserName,
                    grpid: this.responsebody[i].groupId
                };
                this.grouplist.push(groupObject);
            }
            this.dataSource = new MatTableDataSource(this.grouplist);
            this.dataSource.paginator = this.matpaginator;
            this.dataSource.sort = this.sort;
        })
    }
}