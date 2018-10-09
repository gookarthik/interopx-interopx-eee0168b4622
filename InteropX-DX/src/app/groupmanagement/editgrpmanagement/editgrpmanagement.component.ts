import { Component, OnInit, Inject } from '@angular/core';
import { FormGroup, FormControl, Validators, ReactiveFormsModule } from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialogRef, MatTableDataSource } from '@angular/material';
import { GrpmanagementService } from '@app/groupmanagement/grpmanagement.service';
import { UtilityService } from '@app/shared/utility.service';


@Component({
    selector: 'app-editgrp',
    templateUrl: './editgrpmanagement.component.html',
    styleUrls: ['./editgrpmanagement.component.scss']
})
export class EditGrpComponent implements OnInit {
    title: any;
    gname: any;
    gdescription: any;
    groupform: FormGroup;
    grouplist: any;
    responsebody: any;
    dataSource = new MatTableDataSource();
    constructor(public dialogRef: MatDialogRef<EditGrpComponent>, private grpmgmtservice: GrpmanagementService, private util: UtilityService,
        @Inject(MAT_DIALOG_DATA) public data: any) {
        this.title = data.title;
    }
    ngOnInit() {
        this.groupform = new FormGroup({
            name: new FormControl('', [Validators.required, Validators.pattern("^[a-zA-Z ]*$")]),
            description: new FormControl('')
        })
    }

    onsave() {
        const groupObject = {
            GroupName: this.groupform.get('name').value,
            GroupDesc: this.groupform.get('description').value
        }
        this.dialogRef.close(groupObject);
    }
}