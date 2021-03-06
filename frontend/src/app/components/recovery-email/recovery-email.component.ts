import { UserService } from '../../services/user.service';
import { FormBuilder, Validators } from '@angular/forms';
import { Component, OnInit } from '@angular/core';
import { fadeIn } from '../../animations';
import { Router } from '@angular/router';

@Component({
  selector: 'app-recovery-email',
  templateUrl: './recovery-email.component.html',
  styleUrls: ['./recovery-email.component.css'],
  animations: [fadeIn()]
})
export class RecoveryEmailComponent implements OnInit {

  private errorMessage: String;

  constructor(private userService: UserService, private formBuilder: FormBuilder, private router: Router) { }

   recoveryForm = this.formBuilder.group({
    email: ['', Validators.compose([
      Validators.required,
      Validators.pattern('^[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,4}$')
    ])]
  });

  ngOnInit() {
    if (this.userService.getCurrentUser()) {
      this.router.navigate(['/']);
    }
  }

  next() {
    this.userService.setEmail(this.recoveryForm.value['email']);
    this.userService.getUserByEmail(this.userService.getEmail())
    .subscribe(res => {
      this.router.navigate(['question']);
    }, err => {
      this.errorMessage = err['error'];
      this.recoveryForm.reset();
    })
  }
}
