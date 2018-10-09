import { Component, OnInit, Inject, ViewChild } from '@angular/core';
import { FormGroup, FormControl, Validators, ReactiveFormsModule } from '@angular/forms';
import { MatDialog, MAT_DIALOG_DATA, MatDialogRef } from '@angular/material';
import { MatTableDataSource, MatSort, MatPaginator } from '@angular/material';
import { SelectionModel } from '@angular/cdk/collections';
import { GrpmanagementService } from '../../groupmanagement/grpmanagement.service';

@Component({
    selector: 'app-assigngrps',
    templateUrl: './assigngrpstousers.component.html',
    styleUrls: ['./assigngrpstousers.component.scss']
})
export class AssignGrpsToUser implements OnInit {
    title: any;
    responsebody: any;
    grouplist: any = [];
    displayedColumns = ['select', 'grpname', 'createdUname'];
    dataSource = new MatTableDataSource();
    selection = new SelectionModel(true, []);
    @ViewChild(MatPaginator) matpaginator: MatPaginator;

    constructor(private dialog: MatDialog, public dialogRef: MatDialogRef<AssignGrpsToUser>,
        @Inject(MAT_DIALOG_DATA) public data: any, private grpmgmtservice: GrpmanagementService) {

    }
    ngOnInit() {
        this.loadgroups();
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
    loadgroups() {
        this.grpmgmtservice.getListofAllGroups().subscribe((response: Response) => {
            this.responsebody = response.body;
            const result = this.responsebody;
            this.grouplist = [];
            for (let i = 0; i < result.length; i++) {
                const groupObject = {
                    grpname: this.responsebody[i].groupName,
                    grpdesc: this.responsebody[i].groupDesc,
                    createdUname: this.responsebody[i].createdUserName,
                    grpId: this.responsebody[i].groupId
                };
                this.grouplist.push(groupObject);
            }
            this.dataSource = new MatTableDataSource(this.grouplist);
            this.dataSource.paginator = this.matpaginator;

        });
    }
    onsave() {
        this.dialogRef.close(this.selection.selected);
    }
    onclose() {
        this.dialogRef.close();
    }
    applyFilter(filterValue: string) {
        this.dataSource.filter = filterValue.trim().toLowerCase();
    }
}