import { Component, OnInit } from '@angular/core';
import { APIServiceService } from 'src/app/services/apiservice.service';
import { User } from 'src/app/User';

@Component({
  selector: 'app-user-list',
  templateUrl: './user-list.component.html',
  styleUrls: ['./user-list.component.scss'],
})
export class UserListComponent implements OnInit {
  users: User[] = [];
  originalUser: User;
  editedUser = {
    id: '',
    name: '',
    surname: '',
    login: '',
    password: '',
  };
  constructor(public apiService: APIServiceService) {}
  u1 = {
    id: "A",
    name: 'A',
    surname: 'A',
    login: 'A',
    password: 'A',
  }
  u2 = {
    id: "A",
    name: 'A',
    surname: 'A',
    login: 'A',
    password: 'A',
  }
  u3 = {
    id: "dfdfdffffffffffffffffffffffffffffffffffffffffffffffffffA",
    name: 'A',
    surname: 'A',
    login: 'A',
    password: 'A',
  }
  editable = false;
  subscription: any

  ngOnInit(): void {
    this.users.push(this.u1)
    this.users.push(this.u2)
    this.users.push(this.u3)
    this.subscription = this.apiService.getAll().subscribe((usr) => {
      this.users = usr as User[];
    });
  }

  userClicked(user) {
    this.originalUser = user;
    this.editable = true;
    this.editedUser = { ...user };
  }

  saveChanges() {
    for (let i = 0; i < this.users.length; i++) {
      if (this.users[i].id === this.originalUser.id) {
        this.users[i] = { ...this.editedUser };
        this.apiService
          .updateUser(
            this.users[i].name,
            this.users[i].surname,
            this.users[i].login,
            this.users[i].password
          )
          .subscribe();
        break;
      }
    }
    this.clearEditable();
    this.editable = false;
  }

  discardChanges() {
    this.clearEditable();
    this.editable = false;
  }

  clearEditable() {
    this.editedUser.id = '';
    this.editedUser.name = '';
    this.editedUser.surname = '';
    this.editedUser.login = '';
    this.editedUser.password = '';
  }
}
