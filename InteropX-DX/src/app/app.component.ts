import { Component } from '@angular/core';
import { Observable } from 'rxjs/Observable';
import { AuthService } from './auth/auth.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  screenHeight: number;
  notificationOptions = {
    timeOut: 5000,
    showProgressBar: true,
    pauseOnHover: true,
    position: ['bottom', 'right']
  };

  isLoggedIn$: Observable<boolean>;

  constructor(private authService: AuthService) {
    this.screenHeight = window.innerHeight;
    window.onresize = () => {
      // set screenWidth on screen size change
      this.screenHeight = window.innerHeight - 65;
    };

    this.isLoggedIn$ = this.authService.isLoggedIn;
  }
}
