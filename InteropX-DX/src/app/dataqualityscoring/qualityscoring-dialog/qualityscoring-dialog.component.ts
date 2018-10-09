import { Component, OnInit, Inject } from '@angular/core';
import { FormGroup, FormControl, Validators, ReactiveFormsModule } from '@angular/forms';
import { MatTableDataSource, MatSort, MatPaginator } from '@angular/material';
import { MatDialog, MAT_DIALOG_DATA, MatDialogRef } from '@angular/material';
import { DataQualityByExtractionService } from '../dataqualityscoring.service';

@Component({
    selector: 'app-scoringdialog',
    templateUrl: './qualityscoring-dialog.component.html',
    styleUrls: ['./qualityscoring-dialog.component.scss']
})
export class DataqualityDialogComponent implements OnInit {

    displayedColumns = ['resources', 'issues', 'records'];
    dataSource = new MatTableDataSource();
    renderExData: any;
    exId: any;
    exscore: any;
    exiss: any;
    noofRecords: any;
    resname: any;
    actualPatId: any;
    noOfIssues: any;
    isseslist: any;
    issue: any;
    issuedisplay: boolean;
    sumissues: any = 0;
    dataSourceName: any;

    constructor(public dialogRef: MatDialogRef<DataqualityDialogComponent>,
        @Inject(MAT_DIALOG_DATA) public data: any, private dqualityscorebyEx: DataQualityByExtractionService) {
        console.log(data.res);
        const exData = data.res;
        this.dataSourceName = data.res.datasourcename;
        this.loadialogData(exData);

    }
    ngOnInit() {
    }
    onclick() {
        console.log("this clciked");
        var ele = document.getElementById("patient");
        ele.scrollIntoView();
    }
    loadialogData(res) {
        console.log(res);
        this.exId = res.extractionId;
        console.log(this.exId);
        this.dqualityscorebyEx.getExtractionDataById(this.exId).subscribe((response: Response) => {
            console.log(response.body);
            this.renderExData = response.body;
            this.exscore = this.renderExData.score;
            // this.exiss = res.issues;
            this.noofRecords = this.renderExData.dataQuality.length;
            this.resname = this.renderExData.dataQuality[0].resourceList[0].resourceName;

            console.log(this.noofRecords);
            this.sumissues = 0;
            for (let i = 0; i < this.noofRecords; i++) {
                //for (let j = 0; j < this.noofRecords; j++) {
                this.noOfIssues = [];
                this.noOfIssues = this.renderExData.dataQuality[i].resourceList[0].noOfIssues;
                this.sumissues = this.sumissues + this.noOfIssues;
                console.log(this.sumissues);
                // console.log(this.noOfIssues);
                if (this.noOfIssues > 0) {
                    this.actualPatId = this.renderExData.dataQuality[i].ixPatientId;
                    console.log(this.actualPatId);
                    this.isseslist = this.renderExData.dataQuality[i].resourceList[0].issueList;
                    const parsejson = JSON.parse(this.isseslist);
                    console.log(parsejson);
                    this.issue = parsejson[1].categoryDescription;
                    console.log(this.issue);
                }
                const renderextable = [{
                    resources: this.resname,
                    issues: this.sumissues,
                    records: this.noofRecords
                }];
                this.dataSource = new MatTableDataSource(renderextable);
            }
        })


    }
    display() {
        this.issuedisplay = true;
    }
}