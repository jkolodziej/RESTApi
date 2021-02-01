import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { User } from '../User';

@Injectable({
  providedIn: 'root',
})
export class APIServiceService {
  constructor(public httpClient: HttpClient) {}

  getAll(): Observable<User[]> {
    return this.httpClient.get<User[]>(
      'http://localhost:8080/RESTApi/resources/users'
    );
  }

  addUser(
    name: String,
    surname: String,
    login: String,
    password: String
  ): Observable<User> {
    return this.httpClient.post<User>(
      'http://localhost:8080/RESTApi/resources/users/renters',
      {
        name: name,
        surname: surname,
        login: login,
        password: password,
      }
    );
  }

  updateUser(
    name: String,
    surname: String,
    login: String,
    password: String
  ): Observable<User> {
    return this.httpClient.put<User>(
      'http://localhost:8080/RESTApi/resources/users/renters/' + login,
      {
        name: name,
        surname: surname,
        login: login,
        password: password,
      }
    );
  }
}
