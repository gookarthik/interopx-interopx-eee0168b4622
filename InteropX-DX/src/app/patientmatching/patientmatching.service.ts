import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { catchError, map, tap } from 'rxjs/operators';
import { Observable } from 'rxjs';
import { UtilityService } from '../shared/utility.service';
import { environment } from '../../environments/environment';

@Injectable()
export class MatchingService {
    constructor(private http: HttpClient, private util: UtilityService) { }
    getListofAllMatchingData(currentPage, pageSize) {
        return this.http
            .get(environment.apiBaseUrl + '/ix-datasource/groups/conflicts?currentPage=' + currentPage + '&pageSize=' + pageSize, {
                observe: 'response'
            })
            .pipe(
            tap(res => {
                return res;
            }),
            catchError(this.handleError('getListofAllMatchingData', []))
            );
    }
    /*  getListofConflictsData(grpId) {
          return this.http
              .get('http://localhost:2200/groups/conflicts/' + grpId, {
                  observe: 'response'
              })
              .pipe(
              tap(res => {
                  return res;
              }),
              catchError(this.handleError('getListofConflictsData', []))
              );
      }*/
    linkingPatients(json) {
        return this.http
            .post(environment.apiBaseUrl + '/ix-datasource/groups/conflicts/resolve', json, {
                observe: 'response'
            })
            .pipe(
            tap(res => {
                return res;
            }),
            catchError(this.handleError('linkingpatients', []))
            );
    }
    excludePatients(json) {
        return this.http
            .post(environment.apiBaseUrl + '/ix-datasource/groups/conflicts/exclude', json, {
                observe: 'response'
            })
            .pipe(
            tap(res => {
                return res;
            }),
            catchError(this.handleError('excludepatients', []))
            );
    }
    private handleError(operation, result) {
        return (error: any) => {
            if (operation === 'getListofAllMatchingData') {
                this.util.notify('Error in getting list of Matching Data', 'error', error.status);
                return result;
            } if (operation === 'getListofConflictsData') {
                this.util.notify('Error in getting list of Conflicts Data', 'error', error.status);
                return result;
            }
            if (operation === 'linkingpatients') {
                this.util.notify('Error in linking Conflicts Data', 'error', error.status);
                return result;
            }
            if (operation === 'excludepatients') {
                this.util.notify('Error in excluding Conflicts Data', 'error', error.status);
                return result;
            }
        }
    }
}