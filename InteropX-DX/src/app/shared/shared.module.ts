import { NgModule, ModuleWithProviders } from '@angular/core';
import { CommonModule } from '@angular/common';
import { InteropXMaterialModule } from 'interopx-material.module';
import { TableComponent } from './components/table-component/table-component';
import { UtilityService } from './utility.service';
import { LoadingComponent } from './components/loading/loading.component';

@NgModule({
  imports: [CommonModule, InteropXMaterialModule],
  declarations: [TableComponent, LoadingComponent],
  exports: [InteropXMaterialModule, TableComponent, LoadingComponent],
  entryComponents: [],
})
export class SharedModule {
  static forRoot(): ModuleWithProviders {
    return {
      ngModule: SharedModule,
      providers: [UtilityService]
    };
  }
}
