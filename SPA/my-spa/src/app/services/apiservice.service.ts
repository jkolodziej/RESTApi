import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators/catchError';
import { User } from '../User';

@Injectable({
  providedIn: 'root',
})
export class APIServiceService {
  constructor(public httpClient: HttpClient) {}

  getAll(): Observable<User[]> {
    return this.httpClient
      .get<User[]>('http://localhost:8080/RESTApi/resources/users')
      .pipe(catchError(this.handleError));
  }

  addUser(
    name: String,
    surname: String,
    login: String,
    password: String
  ): Observable<User> {
    return this.httpClient
      .post<User>('http://localhost:8080/RESTApi/resources/users/renters', {
        name: name,
        surname: surname,
        login: login,
        password: password,
      })
      .pipe(catchError(this.handleError));
  }

  updateUser(
    name: String,
    surname: String,
    login: String,
    password: String
  ): Observable<User> {
    return this.httpClient
      .put<User>(
        'http://localhost:8080/RESTApi/resources/users/renters/' + login,
        {
          name: name,
          surname: surname,
          login: login,
          password: password,
        }
      )
      .pipe(catchError(this.handleError));
  }

  private handleError(error: HttpErrorResponse) {
    alert(
      'An error occured.\n\
      Error code: ' +
        error.status +
        '\n\
      Error message: ' +
        error.message
    );
    // Return an observable with a user-facing error message.
    return throwError('Something bad happened; please try again later.');
  }
}
