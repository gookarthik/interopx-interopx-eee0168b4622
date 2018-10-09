import { Component, OnInit, Inject, ViewChild } from '@angular/core';
import { FormGroup, FormControl, Validators, ReactiveFormsModule } from '@angular/forms';
import { MatDialog, MAT_DIALOG_DATA, MatDialogRef } from '@angular/material';
import { MatTableDataSource, MatSort, MatPaginator } from '@angular/material';
import { AddUsersToGroup } from '../adduserstogroup/adduserstogroup.component';
import { addedUser_Mgmt } from '../../shared/entities/mock-data';
import { SelectionModel } from '@angular/cdk/collections';
import { UserManagementService } from '../../usermanagement/usermanagement.service';

@Component({
    selector: 'app-addmembers',
    templateUrl: './addmemberstogrp.component.html',
    styleUrls: ['./addmemberstogrp.component.scss']
})
export class AddMemebersToGroup implements OnInit {
    title: any;
    userlistArray: any = [];
    userlistObj: any = {};
    displayedColumns = ['select', 'name', 'email'];
    dataSource = new MatTableDataSource();
    selection = new SelectionModel(true, []);
    @ViewChild(MatPaginator) matpaginator: MatPaginator;


    constructor(private dialog: MatDialog, public dialogRef: MatDialogRef<AddMemebersToGroup>,
        @Inject(MAT_DIALOG_DATA) public data: any, private userservice: UserManagementService) {

    }
    ngOnInit() {
        this.loadusers();
    }
    isAllSelected() {
        const numSelected = this.selection.selected.length;
        const numRows = this.dataSource.data.length;
        return numSelected === numRows;

    }

    /** Selects all rows if they are not all selected; otherwise clear selection. */
    masterToggle() {
        this.isAllSelected() ?
            this.selection.clear() :
            this.dataSource.data.forEach(row => this.selection.select(row));
    }
    loadusers() {
        this.userservice.getListofAllUserData().subscribe((response: Response) => {
            this.userlistObj = response.body;
            this.userlistArray = [];
            for (let i = 0; i < this.userlistObj.length; i++) {
                const renderObject = {
                    username: this.userlistObj[i].username,
                    email: this.userlistObj[i].email,
                    name: this.userlistObj[i].firstName + ' ' + this.userlistObj[i].lastName,
                    id: this.userlistObj[i].userId
                };
                this.userlistArray.push(renderObject);
            }
            this.dataSource = new MatTableDataSource(this.userlistArray);
            this.dataSource.paginator = this.matpaginator;
            // this.dataSource.sort = this.sort;
        });
    }
    applyFilter(filterValue: string) {
        this.dataSource.filter = filterValue.trim().toLowerCase();
    }
    onsave() {
        this.dialogRef.close(this.selection.selected);
    }
    onclose() {
        this.dialogRef.close();
    }

    /*adduserstogrp() {
        const dialogRef = this.dialog.open(AddUsersToGroup, {
            width: '600px',

        });

    }*/
}