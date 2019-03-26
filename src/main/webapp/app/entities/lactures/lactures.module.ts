import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EdusenseSharedModule } from 'app/shared';
import {
    LacturesComponent,
    LacturesDetailComponent,
    LacturesUpdateComponent,
    LacturesDeletePopupComponent,
    LacturesDeleteDialogComponent,
    lacturesRoute,
    lacturesPopupRoute
} from './';

const ENTITY_STATES = [...lacturesRoute, ...lacturesPopupRoute];

@NgModule({
    imports: [EdusenseSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        LacturesComponent,
        LacturesDetailComponent,
        LacturesUpdateComponent,
        LacturesDeleteDialogComponent,
        LacturesDeletePopupComponent
    ],
    entryComponents: [LacturesComponent, LacturesUpdateComponent, LacturesDeleteDialogComponent, LacturesDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class EdusenseLacturesModule {}
