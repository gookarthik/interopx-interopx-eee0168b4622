
import {
  Component,
  OnInit
} from '@angular/core';
import { AppRoutingModule } from '@app/app-routing.module';
import { NAV_ITEMS, NavItem } from '@app/shared/entities/nav-item';
import { animate, state, style, transition, trigger } from '@angular/animations';


@Component({
  selector: 'app-navbar',
  templateUrl: './nav-bar.component.html',
  styleUrls: ['./nav-bar.component.scss'],
  animations: [
    trigger('indicatorRotate', [
      state('collapsed', style({ transform: 'rotate(0deg)', 'margin-left': '50px' })),
      state('expanded', style({ transform: 'rotate(180deg)', 'margin-left': '50px' })),
      transition('expanded <=> collapsed',
        animate('225ms cubic-bezier(0.4,0.0,0.2,1)')
      ),
    ]),
    trigger('exindicatorRotate', [
      state('excollapsed', style({ transform: 'rotate(0deg)', 'margin-left': '50px' })),
      state('exexpanded', style({ transform: 'rotate(180deg)', 'margin-left': '50px' })),
      transition('exexpanded <=> excollapsed',
        animate('225ms cubic-bezier(0.4,0.0,0.2,1)')
      ),
    ])
  ]
})
export class NavBarComponent implements OnInit {
  expanded: boolean;
  exexpanded: boolean;
  navItems: NavItem[];
  constructor(private siteRouting: AppRoutingModule) { }

  ngOnInit() {
    this.navItems = NAV_ITEMS;
  }

  onItemSelected() {
    this.expanded = !this.expanded;
  }

  onexItemSelected() {
    this.exexpanded = !this.exexpanded;
  }
}

