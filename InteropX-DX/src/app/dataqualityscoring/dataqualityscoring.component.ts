import { Component, Input, OnInit, ViewChild, Output, EventEmitter } from '@angular/core';
import { MatDialog, MatColumnDef } from '@angular/material';
import { UtilityService } from '../shared/utility.service';
import { MatTableDataSource, MatSort, MatPaginator } from '@angular/material';
import { SelectionModel } from '@angular/cdk/collections';
import { DataqualityDialogComponent } from './qualityscoring-dialog/qualityscoring-dialog.component';
import { ExtractionDialogComponent } from './extraction-dialog/extraction-dialog.component';
import { DataQualityByExtractionService } from './dataqualityscoring.service';
import { DatasourceService } from '../datasources/datasources.service';
import { Router, ActivatedRoute } from '@angular/router';


@Component({
    selector: 'app-dataquality',
    templateUrl: './dataqualityscoring.component.html',
    styleUrls: ['./dataqualityscoring.component.scss'],

})
export class DataQualityComponent implements OnInit {
    radioOptions = ['By Extractions', 'By Datasource'];
    tableOptions: any = {};
    dstableOptions: any = {};
    renderEx: any = [];
    rendertable: any = [];
    dsname: any;
    dataSourceName: any;
    extrcationTable: boolean = false;
    datasourceTable: boolean = false;
    renderbyds: any;
    rendertablebyds: any;
    qualitydsname: any;
    name: any;

    columns = [
        { columnDef: 'extractionId', header: 'Extraction Id' },
        { columnDef: 'datasourceId', header: 'Data Source Id' },
        { columnDef: 'datasourcename', header: 'Data Source Name' },
        { columnDef: 'score', header: 'Score' },
        { columnDef: 'issues', header: 'Issues' }
    ];
    dscolumns = [
        { columnDef: 'dsId', header: 'Data Source Id' },
        { columnDef: 'dsName', header: 'DatasourceName' },
        { columnDef: 'score', header: 'Score' },
        { columnDef: 'issues', header: 'Issues' }
    ];
    constructor(private dialog: MatDialog, private router: Router, private route: ActivatedRoute, private extractionservice: DataQualityByExtractionService, private dsService: DatasourceService) {

    }
    ngOnInit() {

    }
    optionselect(event) {
        console.log(event);
        if (event.value === 'byextractions') {
            this.tableOptions['tableColumns'] = this.columns;
            this.tableOptions['tableData'] = [];
            this.tableOptions['actions'] = { edit: false, delete: false, viewResults: true };
            this.tableOptions['selectMultipleRows'] = false;
            this.tableOptions['paginator'] = false;
            this.loadExtractions();
            this.extrcationTable = true;
            this.datasourceTable = false;
        }
        if (event.value === 'bydatasource') {
            this.dstableOptions['tableColumns'] = this.dscolumns;
            this.dstableOptions['tableData'] = [];
            this.dstableOptions['actions'] = { edit: false, delete: false, viewResults: true };
            this.dstableOptions['selectMultipleRows'] = false;
            this.dstableOptions['paginator'] = false;
            this.loaddataqualitybyDs();
            this.extrcationTable = false;
            this.datasourceTable = true;
            // this.router.navigate(['/dataQualityByDS']);
        }
    }
    getdataqualityscore(ev) {
        console.log(ev);
        if (ev.actionType === 'View') {
            const res = ev.selectedRow;
            console.log(res);
            const dialogRef = this.dialog.open(DataqualityDialogComponent, {
                width: '1500px',
                data: { res }
            });
        }

    }
    getdsdataqualityscore(ev) {
        if (ev.actionType === 'View') {
            const dsres = ev.selectedRow;
            const dialogRef = this.dialog.open(ExtractionDialogComponent, {
                width: '1500px',
                data: { dsres }
            });
        }

    }
    loadExtractions() {
        this.extractionservice.getListofAllExtractionData().subscribe((response: Response) => {
            console.log(response.body);
            this.renderEx = response.body;
            console.log(this.renderEx);
            this.rendertable = [];
            for (let i = 0; i < this.renderEx.length; i++) {
                this.dsService.getdatasourceById(this.renderEx[i].dsId).subscribe((response: Response) => {
                    console.log(response.body);
                    this.dsname = response.body;
                    this.dataSourceName = this.dsname.dataSourceName;
                    this.rendertable = [{
                        extractionId: this.renderEx[i].etId,
                        datasourceId: this.renderEx[i].dsId,
                        datasourcename: this.dataSourceName,
                        score: this.renderEx[i].overallScore,
                        issues: this.renderEx[i].resourceList[0].noOfIssues
                    }];
                    console.log(this.rendertable);
                    const options = {};
                    options['tableColumns'] = this.columns;
                    options['tableData'] = this.rendertable;
                    options['actions'] = { edit: false, delete: false, viewResults: true };
                    options['selectMultipleRows'] = false;
                    options['paginator'] = true;
                    this.tableOptions = options;
                });


            }

        });
    }
    loaddataqualitybyDs() {
        this.extractionservice.getListofAllDataQualityByDsData().subscribe((res: Response) => {
            console.log(res.body);
            this.renderbyds = res.body;
            for (let i = 0; i < this.renderbyds.length; i++) {
                this.dsService.getdatasourceById(this.renderbyds[i].dsId).subscribe((res: Response) => {
                    console.log(res.body);
                    this.qualitydsname = res.body;
                    this.name = this.qualitydsname.dataSourceName;
                    console.log(this.name);
                    this.rendertablebyds = [{
                        etId: this.renderbyds[i].etId,
                        dsId: this.renderbyds[i].dsId,
                        dsName: this.name,
                        score: this.renderbyds[i].overallScore,
                        issues: this.renderbyds[i].resourceList[0].noOfIssues
                    }];
                    console.log(this.rendertablebyds);
                    const dsoptions = {};
                    dsoptions['tableColumns'] = this.dscolumns;
                    dsoptions['tableData'] = this.rendertablebyds;
                    dsoptions['actions'] = { edit: false, delete: false, viewResults: true };
                    dsoptions['selectMultipleRows'] = false;
                    dsoptions['paginator'] = true;
                    this.dstableOptions = dsoptions;

                });

            }

        });


    }


}
