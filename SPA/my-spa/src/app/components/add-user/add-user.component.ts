import { Component, OnInit } from '@angular/core';
import { APIServiceService } from 'src/app/services/apiservice.service';
import { User } from 'src/app/User';

@Component({
  selector: 'app-add-user',
  templateUrl: './add-user.component.html',
  styleUrls: ['./add-user.component.scss'],
})
export class AddUserComponent implements OnInit {
  public user: User = {
    id: '',
    name: '',
    surname: '',
    login: '',
    password: '',
  };
  constructor(private apiService: APIServiceService) {}

  clearForms() {
    this.user.id = '';
    this.user.name = '';
    this.user.surname = '';
    this.user.login = '';
    this.user.password = '';
  }

  ngOnInit(): void {}

  addUser() {
    this.apiService.addUser(this.user.name, this.user.surname, this.user.login, this.user.password).subscribe()
    this.clearForms();
  }
}
