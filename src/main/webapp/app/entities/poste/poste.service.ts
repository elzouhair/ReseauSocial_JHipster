import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IPoste } from 'app/shared/model/poste.model';

type EntityResponseType = HttpResponse<IPoste>;
type EntityArrayResponseType = HttpResponse<IPoste[]>;

@Injectable({ providedIn: 'root' })
export class PosteService {
  public resourceUrl = SERVER_API_URL + 'api/postes';

  constructor(protected http: HttpClient) {}

  create(poste: IPoste): Observable<EntityResponseType> {
    return this.http.post<IPoste>(this.resourceUrl, poste, { observe: 'response' });
  }

  update(poste: IPoste): Observable<EntityResponseType> {
    return this.http.put<IPoste>(this.resourceUrl, poste, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IPoste>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPoste[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
