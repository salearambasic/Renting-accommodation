import { Component, OnInit, Inject } from '@angular/core';
import { fadeIn } from '../../animations';
import { FormBuilder, Validators } from '@angular/forms';
import { UserService } from '../../services/user.service';
import { Router } from '@angular/router';
import { DOCUMENT } from '@angular/common';
import { CookieService } from 'ngx-cookie-service';
import swal from 'sweetalert2';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
  animations: [fadeIn()]
})
export class LoginComponent implements OnInit {
  cookieValue: string;

  constructor(private cookieService: CookieService, private userService: UserService, private formBuilder: FormBuilder, private router: Router, @Inject(DOCUMENT) private document: any) { }
  
  loginForm = this.formBuilder.group({
    email: ['', Validators.compose([
      Validators.required,
      Validators.pattern('^[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,4}$')
    ])],
    password: ['', Validators.required]
  });

  ngOnInit() {
    if (this.userService.getCurrentUser()) {
      this.router.navigate(['/']);
    }
  }

  login() {
      console.log(this.loginForm.value);
    this.userService.loginUser(this.loginForm.value).subscribe(res => {
        console.log(res);
        if (!!res['token']) {
            
            localStorage.setItem( 'token', res['token'] );
//            localStorage = localStorage.get('token');
            console.log(localStorage);
            this.router.navigate(['/']);
          //sessionStorage.setItem('token', res['token']);
          //const token = storage.get('token');
          //console.log(token);
          //const user = this.userService.getCurrentUser();
          //if (user['role'] === 'AGENT') {
//            this.document.location.href = 'http://localhost:4201';
         /* }
          else if (user['role'] === 'USER') {
            this.router.navigate(['/']);
          }*/
    }}, err => {console.error(err); 
            if(err.status == 404)
            {
                swal(
                'You have to register first!'
              ).then((result) => this.router.navigate(['/register']))
            
            }else if(err.status == 403)
            {
                swal(
                        'Your status is not approved'
                      )
            }else if(err.status == 406)
            {
                swal(
                        'Inavlid username or password'
                      )
            }
        });

    /*.subscribe(res => {
      console.log(res);
      if (!!res['token']) {
          
          localStorage.setItem( 'token', res['token'] );
//          localStorage = localStorage.get('token');
          console.log(localStorage);
          this.router.navigate(['/']);*/
        //sessionStorage.setItem('token', res['token']);
        //const token = storage.get('token');
        //console.log(token);
        //const user = this.userService.getCurrentUser();
        //if (user['role'] === 'AGENT') {
//          this.document.location.href = 'http://localhost:4201';
       /* }
        else if (user['role'] === 'USER') {
          this.router.navigate(['/']);
        }*/
  }
}
