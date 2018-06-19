import { Component, OnInit } from '@angular/core';
import { fadeIn } from '../../animations';
import { ActivatedRoute, Router } from '@angular/router';
import { MessageService } from '../../services/message.service';
import { UserService } from '../../services/user.service';

@Component({
  selector: 'app-message-detail',
  templateUrl: './message-detail.component.html',
  styleUrls: ['./message-detail.component.css'],
  animations: [fadeIn()]
})
export class MessageDetailComponent implements OnInit {

  private messageDirection: String;
  private messageId: Number;
  private message = {};

  constructor(
    private messageService: MessageService,
    private userService: UserService,
    private route: ActivatedRoute,
    private router: Router
  ) { }

  ngOnInit() {
    this.messageDirection = this.route.snapshot.params['direction'];
    this.messageId = this.route.snapshot.params['id'];
    if (this.messageDirection.toLowerCase() !== 'sent' && this.messageDirection.toLowerCase() !== 'received') {
      this.router.navigate(['inbox']);
    } else {
      this.messageService.getReceivedMessage(this.userService.getCurrentUser()['id'], this.messageId)
      .subscribe(res => {
        this.message = res;
        this.markAsRead(this.message);
      }, err => {
        this.router.navigate(['inbox']);
      })

    }
  }

  markAsRead(message) {
    this.messageService.markAsRead(this.userService.getCurrentUser()['id'], message.id)
    .subscribe(res => {
      console.log(res);
    }, err => {
      console.log(err);
    })
  }
}
