import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { catchError, map, tap } from 'rxjs/operators';
import { UtilityService } from '@app/shared/utility.service';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class GrpmanagementService {

  constructor(private http: HttpClient, private util: UtilityService) { }

  creategroup(json) {
    const headers = new HttpHeaders({
      'Content-Type': 'application/json'
    });
    return this.http.post(environment.apiBaseUrl + '/ix-security-management/admin/api/groups', json, {
      headers: headers,
      observe: 'response'
    })
      .pipe(
      tap(res => {
        return res;
      }),
      catchError(this.handleError('createGroup', []))
      );
  }

  getListofAllGroups() {
    const headers = new HttpHeaders({
      'Content-Type': 'application/json'
    });
    return this.http
      .get(environment.apiBaseUrl + '/ix-security-management/admin/api/groups', {
        headers: headers,
        observe: 'response'
      })
      .pipe(
      tap(res => {
        return res;
      }),
      catchError(this.handleError('getListofAllGroups', []))
      );
  }
  assignUsersToGrp(json, id) {
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json'
      }),
      responseType: 'text' as 'text'
    };
    return this.http
      .patch(environment.apiBaseUrl + '/ix-security-management/admin/api/groups/' + id + '/addUsers', json, httpOptions)
      .pipe(
      tap(res => {
        return res;

      }),
      catchError(this.handleError('assigngrps', []))
      );
  }

  deactivategroup(id) {
    return this.http
      .put(environment.apiBaseUrl + '/ix-security-management/admin/api/groups/' + id + '/deactivate', {}, {
        responseType: 'text'
      })
      .pipe(
      tap(res => {
        return res;
      }),
      catchError(this.handleError('deactivategroup', []))
      );
  }

  private handleError(operation, result) {
    return (error: any) => {
      if (operation === 'createGroup') {
        this.util.notify(
          'Error in creating Group',
          'error',
          error.status
        );
        return result;
      }
      else if (operation === 'getListofAllGroups') {
        this.util.notify(
          'Error in fetching list of groups',
          'error',
          error.status);
        return result;
      }
      else if (operation === 'deactivategroup') {
        this.util.notify(
          'Error in deactivating group',
          'error',
          error.status);
        return result;
      }
    }
  }
}
