import { BehaviorSubject } from 'rxjs/internal/BehaviorSubject';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { User } from './user';

@Injectable()
export class AuthService {
  private loggedIn = new BehaviorSubject<boolean>(false);

  get isLoggedIn() {
    // console.log(this.loggedIn.asObservable());
    // return this.loggedIn.asObservable();
    if (localStorage.getItem('userDetails')) {
      this.loggedIn = new BehaviorSubject<boolean>(true);
      return this.loggedIn.asObservable();
    } else {
      return this.loggedIn.asObservable();
    }
  }

  constructor(private router: Router) {}

  login(user: User) {
    if (user.userName === 'johnsmith' && user.password === 'Test@123') {
      this.loggedIn.next(true);
      localStorage.setItem('userDetails', JSON.stringify(user));
      this.router.navigate(['/']);
    }
  }

  logout() {
    localStorage.removeItem('userDetails');
    this.loggedIn.next(false);
    // this.router.navigate(['/login']);
    window.location.reload();
    // return this.loggedIn.asObservable();
  }
}
