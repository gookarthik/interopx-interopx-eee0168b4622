import { Component, OnInit } from '@angular/core';
import { Repository_DATA } from '@app/shared/entities/mock-data';
import { MatDialog } from '@angular/material';
import { RepositoryDialogComponent } from './repository-dialog/repository-dialog.component';
import { RepositoryService } from './repository.service';

@Component({
    selector: 'app-repository',
    templateUrl: './repository.component.html',
    styleUrls: ['./repository.component.scss']
})
export class RepositoryComponent implements OnInit {
    tableOptions: any = {};
    renderRep: any = [];
    isLoadingResults = false;
    columns = [
        { columnDef: 'interopxPatientId', header: 'Internal Id' },
        { columnDef: 'firstName', header: 'Patient First Name' },
        { columnDef: 'lastName', header: 'Patient Last Name' },
        { columnDef: 'gender', header: 'Gender' },
        { columnDef: 'dob', header: 'Date of Birth' },
        { columnDef: 'clinicaldata', header: 'Clinical Data' }
    ];

    constructor(public dialog: MatDialog, private RepoService: RepositoryService) { }

    ngOnInit() {
        this.tableOptions['tableColumns'] = this.columns;
        this.tableOptions['tableData'] = [];
        this.tableOptions['actions'] = { edit: false, delete: false, viewResults: true };
        this.tableOptions['selectMultipleRows'] = false;
        this.tableOptions['paginator'] = false;
        this.loadrepository();
    }
    getrepositoryDetails(ev) {
       // console.log(ev);
        if (ev.actionType === 'View') {
            const data = this.renderRep;
            const dialogRef = this.dialog.open(RepositoryDialogComponent, {
                width: '1200px',
                data: { data }
            });
        }
    }
    loadrepository() {
        this.isLoadingResults = true;
        this.RepoService
            .getListofAllRepository()
            .subscribe((response: Response) => {
                this.isLoadingResults = false;
                this.renderRep = response.body;
                const options = {};
                //console.log(this.renderRep);
                options['tableColumns'] = this.columns;
                options['tableData'] = this.renderRep;
                options['actions'] = { edit: false, delete: false, viewResults: true };
                options['selectMultipleRows'] = false;
                options['paginator'] = true;
                this.tableOptions = options;

            });
    }
} 