import { Component, OnInit, ViewChild, Input, AfterViewInit, Output, EventEmitter, OnChanges } from '@angular/core';
import {
  MatPaginator,
  MatSort,
  MatTableDataSource
} from '@angular/material';
import { SelectionModel } from '@angular/cdk/collections';
import { SortDate } from '@app/shared/entities/sort-date';

@Component({
  selector: 'app-table',
  templateUrl: './table-component.html',
  styleUrls: ['./table-component.scss']
})
export class TableComponent implements OnInit, AfterViewInit, OnChanges {
  @Input() options;
  @Output() action = new EventEmitter<Object>();
  // @Output() selectedPatients = new EventEmitter<[Object]>();
  datasource = new MatTableDataSource();
  staticColumns = [];
  dynamicColumns = [];
  selection = new SelectionModel(true, []);
  selectedPatients: any = [];
  selectMultiple = false;
  paginator = false;
  totalength: number;
  @ViewChild(MatSort) matsort: MatSort;
  @ViewChild(MatPaginator) matpaginator: MatPaginator;

  constructor() {

  }

  ngOnChanges() {
    this.loadTable();
  }

  loadTable() {
    const columns = this.options.tableColumns;
    const data = this.options.tableData;
    const actions = this.options.actions;
    const selectMultipleRows = this.options.selectMultipleRows;
    const tablepaginator = this.options.paginator;
    let extraColumns = [];

    if (actions) {
      extraColumns = columns.concat([{ columnDef: 'actions', header: 'Actions' }]);
      data.forEach(function (e) { e.actions = actions; });
    }

    if (selectMultipleRows) {
      this.selectMultiple = true;
      extraColumns.splice(0, 0, { columnDef: 'select', header: 'Select' });
      extraColumns = extraColumns.concat(columns).filter((x, pos, arr) => {
        return arr.indexOf(x) === pos;
      });
      data.forEach(function (e) { e.select = selectMultipleRows; });
    } else {
      this.selectMultiple = false;
    }



    this.dynamicColumns = columns;
    if (extraColumns.length <= 0) {
      extraColumns = columns;
      this.staticColumns = extraColumns;
      this.staticColumns = this.staticColumns.map(c => c.columnDef);
    } else {
      this.staticColumns = extraColumns;
      this.staticColumns = this.staticColumns.map(c => c.columnDef);
    }

    this.datasource = new MatTableDataSource(data);
    if (tablepaginator) {
      this.paginator = true;
      this.totalength = this.datasource.data.length;
      // this.datasource.paginator = this.matpaginator;

    }
    //this.datasource = new MatTableDataSource(data);
  }

  ngOnInit() {

  }

  ngAfterViewInit() {
    this.datasource.paginator = this.matpaginator;
    this.datasource.sort = this.matsort;
    this.datasource.sortingDataAccessor = (item, property) => {
      switch (property) {
        case SortDate.DATASET_LAST_EXTRACTED: return new Date(item[property]);
        case SortDate.PATIENT_DOB: return new Date(item[property]);
        default: return item[property];
      }
    };
  }
  applyFilter(filterValue: string) {
    filterValue = filterValue.trim();
    filterValue = filterValue.toLowerCase();
    this.datasource.filter = filterValue;
  }

  /** Whether the number of selected elements matches the total number of rows. */
  isAllSelected() {
    this.selectedPatients = this.selection.selected;
    this.action.emit(this.selectedPatients);
    const numSelected = this.selection.selected.length;
    const numRows = this.datasource.data.length;
    return numSelected === numRows;

  }

  /** Selects all rows if they are not all selected; otherwise clear selection. */
  masterToggle() {
    this.isAllSelected()
      ? this.selection.clear()
      : this.datasource.data.forEach(row => this.selection.select(row));
  }

  selecetedAction(title, rowObject) {
    const emitObject = {
      actionType: title,
      selectedRow: rowObject
    };
    this.action.emit(emitObject);
  }
}
