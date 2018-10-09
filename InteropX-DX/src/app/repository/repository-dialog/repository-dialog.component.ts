import { Component, OnInit, Inject } from '@angular/core';
import { Demographics_DATA } from '../../shared/entities/mock-data';
import { ALLERGY_DATA } from '../../shared/entities/mock-data';
import { CONDITION_DATA } from '../../shared/entities/mock-data';
import { MEDSTMT_DATA } from '../../shared/entities/mock-data';
import { OBS_DATA } from '../../shared/entities/mock-data';
import { from } from 'rxjs/internal/observable/from';
import { MatDialog, MAT_DIALOG_DATA, MatDialogRef } from '@angular/material';

@Component({
    selector: 'app-repository-dialog',
    templateUrl: './repository-dialog.component.html',
    styleUrls: ['./repository-dialog.component.scss']
})
export class RepositoryDialogComponent implements OnInit {
    tableOptions: any = {};
    allergyOptions: any = {};
    ConditionOptions: any = {};
    medstmtOptions: any = {};
    observOptions: any = {};
    demodata: any = {};
    json: any = [];
    tableObj: any = [];
    tableArray: any = [];
    constructor(private dialog: MatDialog,
        public dialogRef: MatDialogRef<RepositoryDialogComponent>,
        @Inject(MAT_DIALOG_DATA) public data: any) {
        this.renderDialogData(data);

    }

    ngOnInit() {
        this.tableOptions['tableColumns'] = [
            { columnDef: 'dataSourceName', header: 'Data Source Name' },
            { columnDef: 'firstName', header: 'First Name' },
            { columnDef: 'lastName', header: 'Last Name' },
            { columnDef: 'gender', header: 'Gender' },
            { columnDef: 'dob', header: 'Date of Birth' },
            { columnDef: 'streetAddress', header: 'Address' },
            { columnDef: 'telecom', header: 'Phone' }
        ];
        this.tableOptions['tableData'] = this.tableArray[0];
        this.tableOptions['selectMultipleRows'] = false;

    }
    renderDialogData(data) {
        console.log(data);
        this.demodata = data;
        console.log(this.demodata.data.length);
        for (let i = 0; i < this.demodata.data.length; i++) {
            this.json = JSON.parse(this.demodata.data[0].data);
            console.log(this.json);
            const adressObj = this.json.streetAddress + ',' + this.json.state + ',' + this.json.zip;
            console.log(adressObj);
            //this.tableObj = Object.assign(this.demodata.data[0], this.json);
            // console.log(this.demodata.data);
            this.tableObj[i] = {
                dataSourceName: this.demodata.data[0].dataSourceName,
                firstName: this.demodata.data[0].firstName,
                lastName: this.demodata.data[0].lastName,
                gender: this.demodata.data[0].gender,
                dob: this.demodata.data[0].dob,
                streetAddress: adressObj,
                telecom: this.json.telecom
            }

            this.tableArray = [this.tableObj];
            console.log(this.tableArray);

        }
    }

}
