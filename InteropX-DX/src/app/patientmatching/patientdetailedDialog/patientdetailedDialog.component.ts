import { Component, OnInit, Inject } from '@angular/core';
import { FormGroup, FormControl, Validators, ReactiveFormsModule } from '@angular/forms';
import { MatDialog, MAT_DIALOG_DATA, MatDialogRef } from '@angular/material';

@Component({
    selector: 'app-patientdetail',
    templateUrl: './patientdetailedDialog.component.html',
    styleUrls: ['./patientdetailedDialog.component.scss']
})
export class PatientDetailedComponent implements OnInit {
    tableOptions: any = {};
    tabulardata = [];
    jsonview = [];
    constructor(private dialog: MatDialog,
        public dialogRef: MatDialogRef<PatientDetailedComponent>,
        @Inject(MAT_DIALOG_DATA) public data: any) {
        console.log(data);
        this.tabulardata.push(data.ev);
        this.jsonview.push(data.data);
        console.log(this.tabulardata);
        console.log(this.jsonview);


    }
    ngOnInit() {
        console.log(this.tabulardata);
        this.tableOptions['tableColumns'] = [
            { columnDef: 'Id', header: 'InteropX Id' },
            { columnDef: 'fName', header: 'First Name' },
            { columnDef: 'lname', header: 'Last Name' },
            { columnDef: 'mname', header: 'Middle Name' },
            { columnDef: 'dob', header: 'Date of Birth' },
            { columnDef: 'gender', header: 'Gender' },
            { columnDef: 'phone', header: 'phone' }
        ];
        this.tableOptions['tableData'] = this.tabulardata;
        this.tableOptions['actions'] = false;
        this.tableOptions['selectMultipleRows'] = false;
        this.tableOptions['paginator'] = true;

    }
}
