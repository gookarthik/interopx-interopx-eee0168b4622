import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { DatasetsComponent } from './datasets.component';
import { DatasetsListComponent } from './datasets-list/datasets-list.component';
import { DatasetDetailsComponent } from './dataset-details/dataset-details.component';
import { AllDatasetslistComponent } from './Alldatasetslist/Alldatasetslist.component';
const routes: Routes = [
  {
    path: '',
    children: [
      {
        path: '',
        component: AllDatasetslistComponent
      },
      {
        path: 'list',
        component: DatasetsListComponent
      }, {
        path: 'details/:id',
        component: DatasetDetailsComponent
      }, {
        path: 'selectdslist',
        component: DatasetsComponent
      }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class DataSetsRoutingModule { }
