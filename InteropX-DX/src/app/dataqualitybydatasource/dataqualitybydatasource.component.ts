import { Component, Input, OnInit, ViewChild, Output, EventEmitter } from '@angular/core';
import { MatDialog, MatColumnDef } from '@angular/material';
import { UtilityService } from '../shared/utility.service';
import { MatTableDataSource, MatSort, MatPaginator } from '@angular/material';
import { SelectionModel } from '@angular/cdk/collections';
import { DS_DATA } from '../shared/entities/mock-data';
import { ExtractionDialogComponent } from './extraction-dialog/extraction-dialog.component';
import { DataQualityByDatasourceService } from './dataqualitybydatasource.service';
import { DatasourceService } from '../datasources/datasources.service';
import { Router, ActivatedRoute } from '@angular/router';

@Component({
    selector: 'app-dataqualityds',
    templateUrl: './dataqualitybydatasource.component.html',
    styleUrls: ['./dataqualitybydatasource.component.scss'],

})
export class DataQualityDatasourceComponent implements OnInit {
    radioOptions = ['By Extractions', 'By Datasource'];
    tableOptions: any = {};
    renderbyds: any;
    rendertablebyds: any;
    dsname: any;
    name: any;
    columns = [
        { columnDef: 'dsId', header: 'Data Source Id' },
        { columnDef: 'dsName', header: 'DatasourceName' },
        { columnDef: 'score', header: 'Score' },
        { columnDef: 'issues', header: 'Issues' },
    ];
    constructor(private dialog: MatDialog, private router: Router, private route: ActivatedRoute, private DQbyDsService: DataQualityByDatasourceService, private dsService: DatasourceService) {
    }
    ngOnInit() {
        this.tableOptions['tableColumns'] = this.columns;
        this.tableOptions['tableData'] = [];
        this.tableOptions['actions'] = { edit: false, delete: false, viewResults: true };
        this.tableOptions['selectMultipleRows'] = false;
        this.tableOptions['paginator'] = false;
        this.loaddataqualitybyDs();


    }
    optionselect(event) {
        console.log(event)
        if (event.value === 'byextractions') {
            this.router.navigate(['/dataQuality']);
        }
    }
    getdataqualityscore(ev) {
        if (ev.actionType === 'View') {
            const res = ev.selectedRow;
            const dialogRef = this.dialog.open(ExtractionDialogComponent, {
                width: '1500px',
                data: { res }
            });
        }

    }
    loaddataqualitybyDs() {
        this.DQbyDsService.getListofAllDataQualityByDsData().subscribe((res: Response) => {
            console.log(res.body);
            this.renderbyds = res.body;
            for (let i = 0; i < this.renderbyds.length; i++) {
                this.dsService.getdatasourceById(this.renderbyds[i].dsId).subscribe((res: Response) => {
                    console.log(res.body);
                    this.dsname = res.body;
                    this.name = this.dsname.dataSourceName;
                    console.log(this.name);
                    this.rendertablebyds = [{
                        etId: this.renderbyds[i].etId,
                        dsId: this.renderbyds[i].dsId,
                        dsName: this.name,
                        score: this.renderbyds[i].overallScore,
                        issues: this.renderbyds[i].resourceList[0].noOfIssues
                    }];
                    console.log(this.rendertablebyds);
                    const options = {};
                    options['tableColumns'] = this.columns;
                    options['tableData'] = this.rendertablebyds;
                    options['actions'] = { edit: false, delete: false, viewResults: true };
                    options['selectMultipleRows'] = false;
                    options['paginator'] = false;
                    this.tableOptions = options;

                });

            }

        });


    }

}