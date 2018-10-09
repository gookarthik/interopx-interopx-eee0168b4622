import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HeaderComponent } from './header/header.component';
import { FooterComponent } from './footer/footer.component';
import { NavBarComponent } from './nav-bar/nav-bar.component';
import { MenuListItemComponent } from './nav-bar/menu-list-item/menu-list-item.component';
import { RouterModule } from '@angular/router';
import { InteropXMaterialModule } from './../../interopx-material.module';

@NgModule({
  imports: [CommonModule, RouterModule, InteropXMaterialModule],
  declarations: [HeaderComponent, FooterComponent, NavBarComponent, MenuListItemComponent],
  exports: [HeaderComponent, FooterComponent, NavBarComponent, MenuListItemComponent]
})
export class NavigationModule { }
