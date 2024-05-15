import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-announcement',
  templateUrl: './announcement.component.html',
  styleUrls: ['./announcement.component.css']
})
export class AnnouncementComponent {
  @Input() data:any = {};
  dateDisplay:string=''
  ngOnInit(){
    this.dateDisplay = this.data.date.substring(0,10)
  }
}
