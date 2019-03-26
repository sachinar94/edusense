/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { EdusenseTestModule } from '../../../test.module';
import { ClassroomsDetailComponent } from 'app/entities/classrooms/classrooms-detail.component';
import { Classrooms } from 'app/shared/model/classrooms.model';

describe('Component Tests', () => {
    describe('Classrooms Management Detail Component', () => {
        let comp: ClassroomsDetailComponent;
        let fixture: ComponentFixture<ClassroomsDetailComponent>;
        const route = ({ data: of({ classrooms: new Classrooms(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [EdusenseTestModule],
                declarations: [ClassroomsDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(ClassroomsDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ClassroomsDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.classrooms).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
