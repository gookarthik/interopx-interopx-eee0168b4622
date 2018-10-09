import { Component, OnInit, Inject } from '@angular/core';
import { FormGroup, FormControl, Validators, ReactiveFormsModule } from '@angular/forms';
import { MatDialog, MAT_DIALOG_DATA, MatDialogRef } from '@angular/material';

@Component({
    selector: 'app-addmembers',
    templateUrl: './adduserstogroup.component.html',
    styleUrls: ['./adduserstogroup.component.scss']
})
export class AddUsersToGroup implements OnInit {
    title: any;

    constructor(public dialogRef: MatDialogRef<AddUsersToGroup>,
        @Inject(MAT_DIALOG_DATA) public data: any) {

    }
    ngOnInit() {

    }

}