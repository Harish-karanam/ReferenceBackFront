import { Pipe, PipeTransform } from '@angular/core';
import { Role } from '../../../core/models/user.model';

@Pipe({
  name: 'roleDisplay'
})
export class RoleDisplayPipe implements PipeTransform {
  transform(role: Role): string {
    const roleMap: Record<Role, string> = {
      [Role.USER]: 'User',
      [Role.ADMIN]: 'Administrator'
    };
    return roleMap[role] || role;
  }
}