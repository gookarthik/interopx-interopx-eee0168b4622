import { Component, OnInit, Inject } from '@angular/core';
import { FormGroup, FormControl, Validators, ReactiveFormsModule } from '@angular/forms';
import { MatTableDataSource, MatSort, MatPaginator } from '@angular/material';
import { MatDialog, MAT_DIALOG_DATA, MatDialogRef } from '@angular/material';
import { DSQuality_DATA } from '../../shared/entities/mock-data';
import { DataQualityByDatasourceService } from '../dataqualitybydatasource.service';

@Component({
    selector: 'app-extractionscore',
    templateUrl: './extraction-dialog.component.html',
    styleUrls: ['./extraction-dialog.component.scss']
})
export class ExtractionDialogComponent implements OnInit {
    displayedColumns = ['resources', 'issues', 'records'];
    dataSource = new MatTableDataSource();
    dsName: any;
    totalRecords: any;
    extractionRes: any;
    exId: any;
    overallscore: any;
    issues: any;
    dataqualityArray: any;
    resourcename: any;
    renderdatasourceTable: any = [];
    isseslist: any;
    issuedes: any;
    actualPatId: any;
    issuedescription: boolean = false;
    constructor(public dialogRef: MatDialogRef<ExtractionDialogComponent>,
        @Inject(MAT_DIALOG_DATA) public data: any, private dataqualitybydservice: DataQualityByDatasourceService) {
        console.log(data.res);
        this.dsName = data.res.dsName;
        const dsresponse = data.res;
        this.loadextractData(dsresponse);


    }
    ngOnInit() {
        /* if (this.data.ev.actionType === 'View') {
             this.dsName = this.data.ev.selectedRow.dsName;
         }*/

    }
    loadextractData(exdata) {
        this.renderdatasourceTable = [];
        console.log(exdata.dsId);
        this.dataqualitybydservice.getExtractionsByDsId(exdata.dsId).subscribe((res: Response) => {
            console.log(res.body);
            this.extractionRes = res.body;
            this.totalRecords = this.extractionRes.extractionList.length;
            this.overallscore = this.extractionRes.score;
            this.issues = this.extractionRes.issues;
            for (let i = 0; i < this.totalRecords; i++) {
                this.exId = this.extractionRes.extractionList[i].extractionId;
                this.dataqualityArray = this.extractionRes.extractionList[i].dataQuality;
                console.log(this.dataqualityArray);
                this.resourcename = this.dataqualityArray[0].resourceList[0].resourceName;
                for (let j = 0; j < this.dataqualityArray.length; j++) {
                    const issues = this.dataqualityArray[j].resourceList[0].noOfIssues;
                    console.log(issues);
                    if (issues > 0) {
                        this.actualPatId = this.dataqualityArray[j].ixPatientId;
                        console.log(this.actualPatId);
                        this.isseslist = this.dataqualityArray[j].resourceList[0].issueList;
                        const parsejson = JSON.parse(this.isseslist);
                        console.log(parsejson);
                        this.issuedes = parsejson[1].categoryDescription;
                        console.log(this.issuedes);
                    }
                }
                console.log(this.resourcename);
                if (this.resourcename === 'Patient') {
                    const rendertablebyds = {
                        resources: this.resourcename,
                        issues: this.issues,
                        records: this.dataqualityArray.length,

                    };
                    console.log(rendertablebyds);
                    this.renderdatasourceTable.push(rendertablebyds);
                    this.dataSource = new MatTableDataSource(this.renderdatasourceTable);
                }
                //}
            }
        });

    }
    issuedisplay() {
        this.issuedescription = true;
    }
    onclick() {
        console.log("this clciked");
        var ele = document.getElementById("patient");
        ele.scrollIntoView();
    }
}
