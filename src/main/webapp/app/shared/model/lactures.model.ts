import { Moment } from 'moment';

export interface ILactures {
    id?: number;
    classId?: number;
    timeFrom?: string;
    timeTo?: string;
    startDate?: Moment;
}

export class Lactures implements ILactures {
    constructor(public id?: number, public classId?: number, public timeFrom?: string, public timeTo?: string, public startDate?: Moment) {}
}
