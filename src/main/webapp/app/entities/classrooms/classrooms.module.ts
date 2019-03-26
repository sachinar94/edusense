import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EdusenseSharedModule } from 'app/shared';
import {
    ClassroomsComponent,
    ClassroomsDetailComponent,
    ClassroomsUpdateComponent,
    ClassroomsDeletePopupComponent,
    ClassroomsDeleteDialogComponent,
    classroomsRoute,
    classroomsPopupRoute
} from './';

const ENTITY_STATES = [...classroomsRoute, ...classroomsPopupRoute];

@NgModule({
    imports: [EdusenseSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        ClassroomsComponent,
        ClassroomsDetailComponent,
        ClassroomsUpdateComponent,
        ClassroomsDeleteDialogComponent,
        ClassroomsDeletePopupComponent
    ],
    entryComponents: [ClassroomsComponent, ClassroomsUpdateComponent, ClassroomsDeleteDialogComponent, ClassroomsDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class EdusenseClassroomsModule {}
