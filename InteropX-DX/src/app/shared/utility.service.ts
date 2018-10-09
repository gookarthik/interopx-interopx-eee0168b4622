import { Injectable } from '@angular/core';
import { NotificationsService } from 'angular2-notifications';

export type NotificationType = 'success' | 'error' | 'alert' | 'warn' | 'info';

@Injectable()
export class UtilityService {
  constructor(private notificationService: NotificationsService) { }

  public notify(
    message: string,
    type: NotificationType = 'info',
    title?: string,
    overrideOptions?: any
  ) {
    this.notificationService[type](
      title,
      message,
      overrideOptions
    );
  }

}
