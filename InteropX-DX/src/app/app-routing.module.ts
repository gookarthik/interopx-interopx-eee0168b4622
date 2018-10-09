import { DataSetsModule } from './datasets/datasets.module';
import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { DashboardComponent } from './dashboard/dashboard.component';
import { DatasourcesComponent } from './datasources/datasources.component';
import { SampleComponent } from './sample/sample.component';
import { PageNotFoundComponent } from './page-not-found/page-not-found.component';
import { RepositoryComponent } from './repository/repository.component';
import { TasksComponent } from './tasks/tasks.component';
import { MatchingComponent } from './patientmatching/patientmatching.component';

import { DataQualityComponent } from './dataqualityscoring/dataqualityscoring.component';
import { DataQualityDatasourceComponent } from './dataqualitybydatasource/dataqualitybydatasource.component';
import { GroupMgmtModule } from './groupmanagement/grpmanagement.module';
import { UserMgmtModule } from './usermanagement/usermanagement.module';
import { AuthGuard } from './auth/auth.guard';
import { LoginComponent } from './login/login.component';

const routes: Routes = [
  {
    path: '',
    redirectTo: '/dashboard',
    pathMatch: 'full',
    canActivate: [AuthGuard]
  },
  { path: 'login', component: LoginComponent },
  {
    path: 'dashboard',
    component: DashboardComponent,
    canActivate: [AuthGuard]
  },
  {
    path: 'datasources',
    component: DatasourcesComponent,
    canActivate: [AuthGuard]
  },
  { path: 'sample', component: SampleComponent, canActivate: [AuthGuard] },
  {
    path: 'datasets',
    loadChildren: () => DataSetsModule,
    canActivate: [AuthGuard]
  },
  {
    path: 'repository',
    component: RepositoryComponent,
    canActivate: [AuthGuard]
  },
  { path: 'tasks', component: TasksComponent, canActivate: [AuthGuard] },
  { path: 'matching', component: MatchingComponent, canActivate: [AuthGuard] },
  {
    path: 'userManagement',
    loadChildren: () => UserMgmtModule,
    canActivate: [AuthGuard]
  },
  {
    path: 'dataQuality',
    component: DataQualityComponent,
    canActivate: [AuthGuard]
  },
  {
    path: 'dataQualityByDS',
    component: DataQualityDatasourceComponent,
    canActivate: [AuthGuard]
  },
  {
    path: 'groupMgmt',
    loadChildren: () => GroupMgmtModule,
    canActivate: [AuthGuard]
  },
  { path: '**', component: PageNotFoundComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes, { useHash: true })],
  exports: [RouterModule]
})
export class AppRoutingModule {}
