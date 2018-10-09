import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { DatasetsComponent } from './datasets.component';
import { DatasetsListComponent } from './datasets-list/datasets-list.component';
import { AddNewDataSetComponent } from './add-new-data-set/add-new-data-set.component';
import { DataSetsRoutingModule } from './datasets-routing.module';
import { SharedModule } from '@app/shared/shared.module';
import { DatasetDetailsComponent } from './dataset-details/dataset-details.component';
import { PatientDetailsComponent } from './patient-details/patient-details.component';
import { DatasetsService } from './datasets.service';
import { AllDatasetslistComponent } from './Alldatasetslist/Alldatasetslist.component'

@NgModule({
  imports: [
    CommonModule,
    DataSetsRoutingModule,
    SharedModule,
    FormsModule,
    ReactiveFormsModule
  ],
  declarations: [
    DatasetsListComponent,
    DatasetsComponent,
    AddNewDataSetComponent,
    DatasetDetailsComponent,
    PatientDetailsComponent,
    AllDatasetslistComponent
  ],
  providers: [DatasetsService],
  entryComponents: [AddNewDataSetComponent, PatientDetailsComponent],
  exports: [AddNewDataSetComponent, PatientDetailsComponent],
})
export class DataSetsModule { }
