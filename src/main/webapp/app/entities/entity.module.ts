import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'poste',
        loadChildren: './poste/poste.module#ReseausocialjhPosteModule'
      },
      {
        path: 'commentaire',
        loadChildren: './commentaire/commentaire.module#ReseausocialjhCommentaireModule'
      },
      {
        path: 'eleve',
        loadChildren: './eleve/eleve.module#ReseausocialjhEleveModule'
      },
      {
        path: 'prof',
        loadChildren: './prof/prof.module#ReseausocialjhProfModule'
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ],
  declarations: [],
  entryComponents: [],
  providers: [],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ReseausocialjhEntityModule {}
