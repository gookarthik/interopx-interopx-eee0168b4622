import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { catchError, map, tap } from 'rxjs/operators';
import { Observable } from 'rxjs';
import { UtilityService } from '../shared/utility.service';
import { environment } from '../../environments/environment';

@Injectable()
export class RepositoryService {
    configurl = 'assets/mock.json';
    constructor(private http: HttpClient, private util: UtilityService) { }
    getListofAllRepository() {
        return this.http
            .get(environment.apiBaseUrl + '/ix-repository/Patient/repository/', {
                observe: 'response'
            })
            .pipe(
            tap(res => {
                return res;
            }),
            catchError(this.handleError('getListofAllRepository', []))
            );
    }
    private handleError(operation, result) {
        return (error: any) => {
            if (operation === 'getListofAllRepository') {
                this.util.notify('Error in getting list of Repository', 'error', error.status);
                return result;
            }
        }
    }
}