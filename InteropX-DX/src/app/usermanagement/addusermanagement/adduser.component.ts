import { Component, OnInit, Inject } from '@angular/core';
import { FormGroup, FormControl, Validators, ReactiveFormsModule } from '@angular/forms';
import { MatDialog, MAT_DIALOG_DATA, MatDialogRef } from '@angular/material';

@Component({
    selector: 'app-adduser',
    templateUrl: './adduser.component.html',
    styleUrls: ['./adduser.component.scss']
})
export class AdduserComponent implements OnInit {
    title: any;
    userform: FormGroup;
    constructor(public dialogRef: MatDialogRef<AdduserComponent>,
        @Inject(MAT_DIALOG_DATA) public data: any) {
        this.title = data.title;

    }
    ngOnInit() {
        this.userform = new FormGroup({
            username: new FormControl('', [Validators.required]),
            email: new FormControl('', [Validators.required, Validators.pattern("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")]),
            firstname: new FormControl('', [Validators.required, Validators.pattern("^[a-zA-Z ]*$")]),
            lname: new FormControl('', [Validators.required, Validators.pattern("^[a-zA-Z ]*$")])

        });


    }
    onSave() {
        const UserObject = {
            uname: this.userform.get('username').value,
            email: this.userform.get('email').value,
            fname: this.userform.get('firstname').value,
            lname: this.userform.get('lname').value,

        }
        this.dialogRef.close(UserObject);
    }
}
