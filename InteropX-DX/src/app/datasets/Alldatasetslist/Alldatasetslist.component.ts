import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material';
import { UtilityService } from '../../shared/utility.service';
import { DatasetsService } from '../datasets.service';
import { DatasourceService } from '../../datasources/datasources.service';
import { Router, ActivatedRoute } from '@angular/router';
import { AddNewDataSetComponent } from '../add-new-data-set/add-new-data-set.component';

@Component({
    selector: 'app-datasets',
    templateUrl: './Alldatasetslist.component.html',
    styleUrls: ['./Alldatasetslist.component.scss']
})
export class AllDatasetslistComponent implements OnInit {
    tableOptions: any = {};
    isSecure: string;
    //private fieldArray: Array<any> = [];
    renderDS: any = [];
    securityMethod: any;
    columns = [
        { columnDef: 'dataSetId', header: 'Data Set Id' },
        { columnDef: 'dataSetName', header: 'Data Set' },
        { columnDef: 'scheduleFrequency', header: 'Frequency' },
        { columnDef: 'scheduleTime', header: 'Time' }
    ];
    constructor(private dsetService: DatasetsService, private router: Router, private route: ActivatedRoute, public dialog: MatDialog, private util: UtilityService) {

    }
    ngOnInit() {
        this.tableOptions['tableColumns'] = this.columns;
        this.tableOptions['tableData'] = [];
        this.tableOptions['actions'] = { edit: true, delete: true, viewResults: false };
        this.tableOptions['selectMultipleRows'] = false;
        this.tableOptions['paginator'] = true;
        this.loadDataSets();
    }
    loadDataSets() {
        this.dsetService
            .getListofAllDatasets()
            .subscribe((response: Response) => {
                this.renderDS = response.body;
                const options = {};
                options['tableColumns'] = this.columns;
                options['tableData'] = this.renderDS;
                options['actions'] = { edit: true, delete: true, viewResults: false };
                options['selectMultipleRows'] = false;
                options['paginator'] = true;
                this.tableOptions = options;

            });
    }
    createDataSet() {
        this.router.navigate(['/datasets/selectdslist']);
    }
    getActionDetails(ev) {
        const id = ev.selectedRow.dataSetId;
        const updated = ev.selectedRow.lastUpdated;
        const datasources = ev.selectedRow.data_sources;
        const action = ev;
        if (action.actionType === 'Edit') {
            const title = 'Edit';
            const dialogRef = this.dialog.open(AddNewDataSetComponent, {
                width: '600px',
                data: { ev, title }
            });
            dialogRef.afterClosed().subscribe(result => {
                if (result !== '' && result !== undefined) {
                    console.log(result);
                    const json = {
                        dataSetName: result.dataSetName,
                        dataSetId: id,
                        data_sources: datasources,
                        scheduleFrequency: result.scheduleFrequency,
                        scheduleTime: result.scheduleTime,
                        lastUpdated: updated
                    };
                    this.dsetService.editDatasetRow(json).subscribe(data => {
                        this.loadDataSets();
                        this.util.notify(
                            'Data Set Updated Successfully',
                            'success',
                            'Success'
                        );
                    });
                }
            });
        }
        if (action.actionType === 'Delete') {
            const x = ev.selectedRow;
            //var result = confirm("Are you Sure?");
            //if (result) {
            this.tableOptions['tableData'].splice(x, 1)
            this.dsetService.deleteDataset(x.dataSetId).subscribe(data => {
                this.loadDataSets();
                this.util.notify(
                    'Data Set deleted Successfully',
                    'success',
                    'Success'
                );
            });
            //}
        }
    }
}