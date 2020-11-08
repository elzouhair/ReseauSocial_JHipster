import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Prof } from 'app/shared/model/prof.model';
import { ProfService } from './prof.service';
import { ProfComponent } from './prof.component';
import { ProfDetailComponent } from './prof-detail.component';
import { ProfUpdateComponent } from './prof-update.component';
import { ProfDeletePopupComponent } from './prof-delete-dialog.component';
import { IProf } from 'app/shared/model/prof.model';

@Injectable({ providedIn: 'root' })
export class ProfResolve implements Resolve<IProf> {
  constructor(private service: ProfService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IProf> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Prof>) => response.ok),
        map((prof: HttpResponse<Prof>) => prof.body)
      );
    }
    return of(new Prof());
  }
}

export const profRoute: Routes = [
  {
    path: '',
    component: ProfComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Profs'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ProfDetailComponent,
    resolve: {
      prof: ProfResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Profs'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ProfUpdateComponent,
    resolve: {
      prof: ProfResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Profs'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ProfUpdateComponent,
    resolve: {
      prof: ProfResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Profs'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const profPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: ProfDeletePopupComponent,
    resolve: {
      prof: ProfResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Profs'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
