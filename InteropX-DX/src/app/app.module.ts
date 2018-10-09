/**
 * Copyright 2018, InteropX. All rights reserved.
 */
// Vendor
import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { ChartsModule } from 'ng2-charts';
import { HttpModule } from '@angular/http';
import { HttpClientModule } from '@angular/common/http';

// Modules
import { InteropXMaterialModule } from 'interopx-material.module';
import { AppRoutingModule } from './app-routing.module';
import { SimpleNotificationsModule } from 'angular2-notifications';
import { SharedModule } from './shared/shared.module';
import { NavigationModule } from './navigation/navigation.module';

// Components
import { AppComponent } from './app.component';
import { LoginComponent } from './login/login.component';
import { DashboardComponent } from './dashboard/dashboard.component';
import { DatasourcesComponent } from './datasources/datasources.component';
import { SampleComponent } from './sample/sample.component';
import { AddDatasourcesComponent } from './datasources/addnewdatasource/addnewdatasource.component';
import { PageNotFoundComponent } from './page-not-found/page-not-found.component';
import { RepositoryComponent } from './repository/repository.component';
import { RepositoryDialogComponent } from './repository/repository-dialog/repository-dialog.component';
import { TasksComponent } from './tasks/tasks.component';
import { MatchingComponent } from './patientmatching/patientmatching.component';
import { DatasourceService } from './datasources/datasources.service';
import { RepositoryService } from './repository/repository.service';
import { MatchingService } from './patientmatching/patientmatching.service';
import { PatientDetailedComponent } from './patientmatching/patientdetailedDialog/patientdetailedDialog.component';
// import { UserManagementComponent } from './usermanagement/usermanagement.component';
// import { AdduserComponent } from './usermanagement/addusermanagement/adduser.component';
import { UserManagementService } from './usermanagement/usermanagement.service';
import { DataQualityComponent } from './dataqualityscoring/dataqualityscoring.component';
import { DataqualityDialogComponent } from './dataqualityscoring/qualityscoring-dialog/qualityscoring-dialog.component';
import { DataQualityDatasourceComponent } from './dataqualitybydatasource/dataqualitybydatasource.component';
import { ExtractionDialogComponent } from './dataqualityscoring/extraction-dialog/extraction-dialog.component';
import { DataQualityByExtractionService } from './dataqualityscoring/dataqualityscoring.service';
// import { DataQualityByDatasourceService } from './dataqualitybydatasource/dataqualitybydatasource.service';
import { AuthGuard } from './auth/auth.guard';
import { AuthService } from './auth/auth.service';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    DashboardComponent,
    DatasourcesComponent,
    AddDatasourcesComponent,
    SampleComponent,
    PageNotFoundComponent,
    RepositoryComponent,
    RepositoryDialogComponent,
    TasksComponent,
    MatchingComponent,
    PatientDetailedComponent,
    // UserManagementComponent,
    // AdduserComponent,
    DataQualityComponent,
    DataqualityDialogComponent,
    DataQualityDatasourceComponent,
    ExtractionDialogComponent
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    FormsModule,
    ChartsModule,
    ReactiveFormsModule,
    NavigationModule,
    HttpModule,
    HttpClientModule,
    InteropXMaterialModule,
    SimpleNotificationsModule.forRoot(),
    SharedModule.forRoot(),
    AppRoutingModule
  ],
  providers: [
    DatasourceService,
    RepositoryService,
    MatchingService,
    DataQualityByExtractionService,
    // DataQualityByDatasourceService,
    UserManagementService,
    AuthGuard,
    AuthService
  ],
  entryComponents: [
    AddDatasourcesComponent,
    RepositoryDialogComponent,
    PatientDetailedComponent,
    DataqualityDialogComponent,
    ExtractionDialogComponent
  ],
  exports: [
    AddDatasourcesComponent,
    RepositoryDialogComponent,
    PatientDetailedComponent,
    DataqualityDialogComponent,
    ExtractionDialogComponent
  ],
  bootstrap: [AppComponent]
})
export class AppModule {}
