import { Component, Input, OnInit, ViewChild, Output, EventEmitter } from '@angular/core';
import { MatDialog, MatColumnDef } from '@angular/material';
import { UtilityService } from '../shared/utility.service';
import { MatTableDataSource, MatSort, MatPaginator } from '@angular/material';
import { SelectionModel } from '@angular/cdk/collections';
import { Interopx_DATA, Conflict_DATA } from '../shared/entities/mock-data';
import { MatchingService } from './patientmatching.service';
import { PatientDetailedComponent } from './patientdetailedDialog/patientdetailedDialog.component';

@Component({
    selector: 'app-matching',
    templateUrl: './patientmatching.component.html',
    styleUrls: ['./patientmatching.component.scss'],

})
export class MatchingComponent implements OnInit {

    tableOptions: any = {};
    rendermatch: any = [];
    conflicttableOptions: any = {};
    displayedColumns: string[] = ['select', 'action', 'SId', 'fName', 'lname', 'mname', 'birth', 'gender', 'phone'];
    dataSource = new MatTableDataSource();
    selection = new SelectionModel(true, []);
    existingdisplayedColumns = ['group', 'Id', 'fName', 'lname', 'mname', 'dob', 'gender', 'phone'];
    existingdataSource = new MatTableDataSource();
    currentPage: number;
    conflicts: any = [];
    conflictedArray: any = [];
    parsejson: any;
    mismatchelements: any;
    pageSize: number;
    next: boolean = false;
    previous: boolean = false;
    isLoadingResults = false;
    filterArray = [];

    @ViewChild(MatPaginator) matpaginator: MatPaginator;
    constructor(private matchingservice: MatchingService, private dialog: MatDialog) {

    }

    ngOnInit() {
        this.loadGroupsData();
    }

    isAllSelected() {
        const numSelected = this.selection.selected.length;
        const numRows = this.dataSource.data.length;
        return numSelected === numRows;
    }

    /** Selects all rows if they are not all selected; otherwise clear selection. */
    masterToggle() {
        this.isAllSelected() ?
            this.selection.clear() :
            this.dataSource.data.forEach(row => this.selection.select(row));
    }
    loadGroupsData() {
        this.currentPage = 0;
        this.pageSize = 1;
        this.isLoadingResults = true;
        this.matchingservice
            .getListofAllMatchingData(this.currentPage, this.pageSize)
            .subscribe((response: Response) => {
                this.isLoadingResults = false;
                this.rendermatch = response.body;
                const currentpage = this.rendermatch.currentPage + 1;
                const totalpages = this.rendermatch.totalRecords
                this.parsejson = JSON.parse(this.rendermatch.groups[0].interopxRecord);

                const json = [{
                    group: currentpage + ' Out of ' + totalpages,
                    Id: this.rendermatch.groups[0].interopxId,
                    dob: this.parsejson.dateOfBirth,
                    fName: this.parsejson.firstName,
                    lname: this.parsejson.lastName,
                    mname: this.parsejson.middleInitial,
                    gender: this.parsejson.adminGender,
                    phone: this.parsejson.telecom
                }];
                this.existingdataSource = new MatTableDataSource(json);
                this.conflicts = this.rendermatch.groups[0].conflicts;
                this.conflictedArray = [];
                for (let i = 0; i < this.conflicts.length; i++) {
                    const Conflictedjson = JSON.parse(this.conflicts[i].conflictedRecord);

                    const conflictedRecord = {
                        action: this.conflicts[i].status,
                        SId: this.conflicts[i].recordId,
                        birth: Conflictedjson.dateOfBirth,
                        fName: Conflictedjson.firstName,
                        lname: Conflictedjson.lastName,
                        mname: Conflictedjson.middleInitial,
                        gender: Conflictedjson.adminGender,
                        phone: Conflictedjson.telecom

                    };
                    this.mismatchelements = JSON.parse(this.conflicts[i].misMatchedElements);
                    this.conflictedArray.push(conflictedRecord);
                    this.dataSource = new MatTableDataSource(this.conflictedArray);
                    this.dataSource.paginator = this.matpaginator;

                }
                if (currentpage === 1) {
                    this.previous = true;
                    this.next = false;
                } else {
                    this.previous = false;
                    this.next = false;
                }

            });
    }
    nextGroup() {
        this.selection = new SelectionModel(true, []);
        this.isLoadingResults = true;
        this.rendermatch = [];
        this.parsejson = [];
        const currentPage = this.currentPage++;
        this.pageSize = this.pageSize++;
        this.matchingservice
            .getListofAllMatchingData(this.currentPage, this.pageSize)
            .subscribe((response: Response) => {
                this.isLoadingResults = false;
                this.rendermatch = response.body;
                const currentpage = this.rendermatch.currentPage + 1;
                const totalpages = this.rendermatch.totalRecords
                this.parsejson = JSON.parse(this.rendermatch.groups[0].interopxRecord);
                const json = [{
                    group: currentpage + ' Out of ' + totalpages,
                    Id: this.rendermatch.groups[0].interopxId,
                    dob: this.parsejson.dateOfBirth,
                    fName: this.parsejson.firstName,
                    lname: this.parsejson.lastName,
                    mname: this.parsejson.middleInitial,
                    gender: this.parsejson.adminGender,
                    phone: this.parsejson.telecom
                }];
                this.existingdataSource = new MatTableDataSource(json);
                this.conflicts = this.rendermatch.groups[0].conflicts;
                this.conflictedArray = [];
                this.mismatchelements = [];
                for (let i = 0; i < this.conflicts.length; i++) {
                    this.mismatchelements = [];
                    const Conflictedjson = JSON.parse(this.conflicts[i].conflictedRecord);
                    const conflictedRecord = {
                        action: this.conflicts[i].status,
                        SId: this.conflicts[i].recordId,
                        birth: Conflictedjson.dateOfBirth,
                        fName: Conflictedjson.firstName,
                        lname: Conflictedjson.lastName,
                        mname: Conflictedjson.middleInitial,
                        gender: Conflictedjson.adminGender,
                        phone: Conflictedjson.telecom

                    };
                    this.mismatchelements = JSON.parse(this.conflicts[i].misMatchedElements);
                    const Array = [];
                    this.conflictedArray.push(conflictedRecord);
                    this.dataSource = new MatTableDataSource(this.conflictedArray);
                    this.dataSource.paginator = this.matpaginator;

                }
                if (currentpage === totalpages) {
                    this.next = true;
                    this.previous = false;
                } else {
                    this.next = false;
                    this.previous = false;
                }
            });
    }
    prevGroup() {
        this.selection = new SelectionModel(true, []);
        this.rendermatch = [];
        this.parsejson = [];
        this.isLoadingResults = true;
        const currentPage = this.currentPage--;
        //this.pageSize = this.pageSize++;
        this.matchingservice
            .getListofAllMatchingData(this.currentPage, this.pageSize)
            .subscribe((response: Response) => {
                this.isLoadingResults = false;
                this.rendermatch = response.body;
                const currentpage = this.rendermatch.currentPage + 1;
                const totalpages = this.rendermatch.totalRecords
                this.parsejson = JSON.parse(this.rendermatch.groups[0].interopxRecord);
                const json = [{
                    group: currentpage + ' Out of ' + totalpages,
                    Id: this.rendermatch.groups[0].interopxId,
                    dob: this.parsejson.dateOfBirth,
                    fName: this.parsejson.firstName,
                    lname: this.parsejson.lastName,
                    mname: this.parsejson.middleInitial,
                    gender: this.parsejson.adminGender,
                    phone: this.parsejson.telecom
                }];
                this.existingdataSource = new MatTableDataSource(json);
                this.conflicts = this.rendermatch.groups[0].conflicts;
                this.conflictedArray = [];
                for (let i = 0; i < this.conflicts.length; i++) {
                    const Conflictedjson = JSON.parse(this.conflicts[i].conflictedRecord);
                    const conflictedRecord = {
                        action: this.conflicts[i].status,
                        SId: this.conflicts[i].recordId,
                        birth: Conflictedjson.dateOfBirth,
                        fName: Conflictedjson.firstName,
                        lname: Conflictedjson.lastName,
                        mname: Conflictedjson.middleInitial,
                        gender: Conflictedjson.adminGender,
                        phone: Conflictedjson.telecom

                    };
                    const mismatchelements = JSON.parse(this.conflicts[i].misMatchedElements);
                    this.conflictedArray.push(conflictedRecord);
                    this.dataSource = new MatTableDataSource(this.conflictedArray);
                    this.dataSource.paginator = this.matpaginator;

                }
                if (currentpage === 1) {
                    this.previous = true;
                    this.next = false;
                } else {
                    this.previous = false;
                    this.next = false;
                }

            });

    }
    link() {
        let grp = this.rendermatch.groups[0];
        const conArray = this.rendermatch.groups[0].conflicts;
        grp.conflicts = [];
        for (let i = 0; i < this.selection.selected.length; i++) {
            const selectedConflict = conArray.filter(x => {
                return x.recordId === this.selection.selected[i].SId;
            });
            if (selectedConflict[0] !== 'undefined') {
                grp.conflicts.push(selectedConflict[0]);
            }
        }
        this.matchingservice.linkingPatients(grp).subscribe((res: Response) => {
            const linkres = res.body;
        });

    }
    exclude() {
        let excludegrp = this.rendermatch.groups[0];
        const exArray = this.rendermatch.groups[0].conflicts;
        excludegrp.conflicts = [];
        for (let i = 0; i < this.selection.selected.length; i++) {
            const excludedConflict = exArray.filter(x => {
                return x.recordId === this.selection.selected[i].SId;
            });
            if (excludedConflict[0] !== 'undefined') {
                excludegrp.conflicts.push(excludedConflict[0]);
            }

        }
        this.matchingservice.excludePatients(excludegrp).subscribe((res: Response) => {
            const excluderes = res.body;

        });

    }
    openDialog(ev) {
        const data = this.parsejson;
        const dialogRef = this.dialog.open(PatientDetailedComponent, {
            width: '1000px',
            data: { ev, data }
        });
    }
}

