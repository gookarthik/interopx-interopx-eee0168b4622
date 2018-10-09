import { Component, OnInit } from '@angular/core';
import { TASKS_DATA } from '@app/shared/entities/mock-data';
import { MatDialog } from '@angular/material';

@Component({
    selector: 'app-repository',
    templateUrl: './tasks.component.html',
    styleUrls: ['./tasks.component.scss']
})
export class TasksComponent implements OnInit {
    tableOptions: any = {};

    constructor(public dialog: MatDialog) { }
    ngOnInit() {
        this.tableOptions['tableColumns'] = [
            { columnDef: 'dsName', header: 'Data Set Name' },
            { columnDef: 'sdate', header: 'Start Date' },
            { columnDef: 'edate', header: 'End Date' },
            { columnDef: 'status', header: 'Status' },
            { columnDef: 'target', header: 'Target' },
            { columnDef: 'notes', header: 'Notes' }
        ];
        this.tableOptions['tableData'] = TASKS_DATA;
        //this.tableOptions['actions'] = { edit: false, delete: false, viewResults: false };
        this.tableOptions['selectMultipleRows'] = false;

    }
}