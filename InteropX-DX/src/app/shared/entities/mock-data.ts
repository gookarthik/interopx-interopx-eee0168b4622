export const SAMPLE_DATA: any[] =
  [
    {
      name: 'Smith Addie',
      age: '20',
      gender: 'Male'
    },
    {
      name: 'Leslie Winkle',
      age: '19',
      gender: 'Female'
    },
    {
      name: 'David Bloom',
      age: '16',
      gender: 'Male'
    },
    {
      name: 'Dobrev Nina',
      age: '18',
      gender: 'Female'
    },
  ];
export const DATASOURCE_DATA: any[] =
  [
    {
      dsId: 'IX-12345',
      dsName: 'Hospital',
      endpoint: 'http://54.173.80.197:8100/hospital ',
      connector: 'Bulk API (HL7 FHIR)',
      security: 'open'
    },
    {
      dsId: 'IX-14523',
      dsName: 'PCP',
      endpoint: 'http://54.173.80.197:8100/pcp ',
      connector: 'Bulk API (HL7 FHIR)',
      security: 'secure'
    },
    {
      dsId: 'IX-12453',
      dsName: 'One Lab',
      endpoint: 'http://54.173.80.197:8100/onelab ',
      connector: 'Bulk API (HL7 FHIR)',
      security: 'open'
    },
    {
      dsId: 'IX-12534',
      dsName: 'Cerner',
      endpoint: 'https://fhir-open.sandboxcerner.com/dstu2/0b8a0111-e8e6-4c26-a91c-5069cbc6b1ca  ',
      connector: 'Argonaut  API (HL7 FHIR)',
      security: 'Secure'
    },
    {
      dsId: 'IX-34521',
      dsName: 'Epic',
      endpoint: 'https://open-ic.epic.com/FHIR/api/FHIR/DSTU2',
      connector: 'Argonaut  API (HL7 FHIR)',
      security: 'Open'
    }
  ];

export const DATASET_DATA: any[] =
  [
    {
      dsId: 'IX-56789',
      dsName: 'DataSet-1',
      target: 'jdbc:mysql://54.173.80.197:3306/Database1',
      schedule: 'Daily',
      lastExtracted: '2018-06-06 01:41:37'
    },
    {
      dsId: 'IX-57896',
      dsName: 'DataSet-2',
      target: 'jdbc:postgres://54.173.80.197:5432/Database2',
      schedule: 'Monthly',
      lastExtracted: '2018-05-06 02:43:37'
    },
    {
      dsId: 'IX-56897',
      dsName: 'DataSet-3',
      target: 'jdbc:mssql://54.173.80.197:1434/Database3',
      schedule: 'Weekly',
      lastExtracted: '2018-05-31 03:45:37'
    },
    {
      dsId: 'IX-56798',
      dsName: 'DataSet-4',
      target: 'jdbc:mysql://54.173.80.197:3306/Database4',
      schedule: 'Daily',
      lastExtracted: '2018-06-06 04:47:37'
    },
    {
      dsId: 'IX-67895',
      dsName: 'DataSet-5',
      target: 'jdbc:postgres://54.173.80.197:5432/Database5',
      schedule: 'Weekly',
      lastExtracted: '2018-05-31 05:49:37'
    }
  ];

export const DATASET_DATASOURCE_DATA: any[] =
  [
    {
      position: 'Patient Id:10001,First Name:AADEN,Last Name: SCOTT,Gender: Male, Date of Birth:04-01-1969',
      possible: 'Patient Id:10001,First Name:AADEN,Last Name: SCOTT,Gender: Male, Date of Birth:04-01-1969',

    },
    {
      position: 'Patient Id:10001,First Name:AADEN,Last Name: SCOTT,Gender: Male, Date of Birth:04-01-1969',
      possible: 'Patient Id:10001,First Name:AADEN,Last Name: SCOTT,Gender: Male, Date of Birth:04-01-1969',
    },
    {
      position: 'Patient Id:10001,First Name:AADEN,Last Name: SCOTT,Gender: Male, Date of Birth:04-01-1969',
      possible: 'Patient Id:10001,First Name:AADEN,Last Name: SCOTT,Gender: Male, Date of Birth:04-01-1969',
    },
    {
      position: 'Patient Id:10001,First Name:AADEN,Last Name: SCOTT,Gender: Male, Date of Birth:04-01-1969',
      possible: 'Patient Id:10001,First Name:AADEN,Last Name: SCOTT,Gender: Male, Date of Birth:04-01-1969',
    },
    {
      position: 'Patient Id:10001,First Name:AADEN,Last Name: SCOTT,Gender: Male, Date of Birth:04-01-1969',
      possible: 'Patient Id:10001,First Name:AADEN,Last Name: SCOTT,Gender: Male, Date of Birth:04-01-1969',
    }
  ];

export const DATASET_PATIENT_DATA: any[] = [
  {
    patientId: 'PA-34456',
    fName: 'John',
    lName: 'Doe',
    gender: 'Male',
    dob: '1949-01-22'
  },
  {
    patientId: 'PA-34457',
    fName: 'Aliya',
    lName: 'Wilson',
    gender: 'Female',
    dob: '1958-06-10'
  },
  {
    patientId: 'PA-34458',
    fName: 'Arav',
    lName: 'Miller',
    gender: 'Male',
    dob: '1954-08-09'
  }
];

export const EXTRACTED_ALLERGY_DATA: any[] = [
  {
    dsName: 'Data Source-1',
    allergyId: '649727',
    status: 'Active',
    code: '2670',
    display: 'Codeine',
    patient: 'AADEN SCOTT'
  },
  {
    dsName: 'Data Source-1',
    allergyId: '1196366',
    status: 'Active',
    code: '2677',
    display: 'Pollen',
    patient: 'AADEN SCOTT'
  },
  {
    dsName: 'Data Source-1',
    allergyId: '649725',
    status: 'Active',
    code: '2670',
    display: 'Codeine',
    patient: 'AADEN SCOTT'
  }
];

export const EXTRACTED_CONDITION_DATA: any[] = [
  {
    dsName: 'Data Source-1',
    conditionId: 'p831866',
    status: 'Active',
    code: '53741008',
    display: 'Coronary arteriosclerosis',
    patient: 'AADEN SCOTT'
  },
  {
    dsName: 'Data Source-2',
    conditionId: 'd5160456',
    status: 'Active',
    code: '250.00',
    display: 'Diabetes mellitus without mention of complication, type II or unspecified type, not stated as uncontrolled',
    patient: 'AADEN SCOTT'
  },
  {
    dsName: 'Data Source-3',
    conditionId: 'd6902645',
    status: 'Active',
    code: '493.92',
    display: 'Asthma, unspecified with (acute) exacerbation',
    patient: 'AADEN SCOTT'
  },
  {
    dsName: 'Data Source-4',
    conditionId: 'p831866',
    status: 'Active',
    code: '53741008',
    display: 'Coronary arteriosclerosis',
    patient: 'AADEN SCOTT'
  }
];

export const EXTRACTED_MEDICATION_DATA: any[] = [
  {
    dsName: 'Data Source-1',
    medicationId: '1030509',
    status: 'Active',
    display: 'Prednisone',
    patient: 'AALIYAH WILSON'
  },
  {
    dsName: 'Data Source-2',
    medicationId: '9390407',
    status: 'Active',
    display: 'DOPamine 200 mg [3 mcg/kg/min] + Dextrose 5% in Water intravenous solution 500 mL',
    patient: 'AADEN SCOTT'
  },
  {
    dsName: 'Data Source-3',
    medicationId: '9389608',
    status: 'Active',
    display: 'Glucose 50 MG/ML / Potassium Chloride 0.02 MEQ/ML Injectable Solution',
    patient: 'AARAN MILLER'
  }
];

export const EXTRACTED_OBSERVATION_DATA: any[] = [
  {
    dsName: 'Data Source-1',
    observationId: 'M1452481',
    category: 'vital-signs',
    code: '3139-3',
    display: 'BSA Measured',
    value: '1.8',
    patient: 'LISA SCOTT'
  },
  {
    dsName: 'Data Source-2',
    observationId: 'M1452483',
    category: 'vital-signs',
    code: '39156-5',
    display: 'Body Mass Index Measured',
    value: '23.26 m2',
    patient: 'AADEN SCOTT'
  },
  {
    dsName: 'Data Source-3',
    observationId: 'M1452477',
    category: 'vital-signs',
    code: '8302-2',
    display: 'Height/Length Measured',
    value: 'vital-signs',
    patient: 'AARAN MILLER'
  },
  {
    dsName: 'Data Source-4',
    observationId: 'p831866',
    category: 'vital-signs',
    code: '29463-7',
    display: 'Weight Measured',
    value: '68 kg',
    patient: 'AALIYAH WILSON'
  }
];
export const Repository_DATA: any[] = [
  {
    Id: 'P-YbqyE1r4',
    fName: 'ALLEN',
    lname: 'MILLER',
    gender: 'Male',
    dob: '1969-02-08',
    clinicaldata: 'Allergy Intolerance'
  },
  {
    Id: 'P-YbqyE1r4',
    fName: 'AALIYAH',
    lname: 'WILLIAMS',
    gender: 'Female',
    dob: '1953-06-24',
    clinicaldata: 'Allergy Intolerance'
  },
  {
    Id: 'P-YbqyE1r4',
    fName: 'AADEN',
    lname: 'SCOTT',
    gender: 'Male',
    dob: '1989-03-01',
    clinicaldata: 'Allergy Intolerance'
  },
  {
    Id: 'P-YbqyE1r4',
    fName: 'LISA',
    lname: 'CERNER',
    gender: 'Female',
    dob: '1949-01-18',
    clinicaldata: 'Allergy Intolerance'
  }
];
export const Demographics_DATA: any[] = [
  {
    dsName: 'PA-34456',
    fName: 'AALIYAH',
    lName: 'WILSON',
    gender: 'Male',
    dob: '1949-01-22',
    address: '2842 E 21st Street Kansas City, MO 64145 USA',
    status: 'Married',
    phone: '8169729987'
  },
  {
    dsName: 'PA-34456',
    fName: 'AADEN',
    lName: 'SCOTT',
    gender: 'Male',
    dob: '1949-01-22',
    address: '2842 E 21st Street Kansas City, MO 64145 USA',
    status: 'Married',
    phone: '8169729987'
  },
  {
    dsName: 'PA-34456',
    fName: 'AARAN',
    lName: 'MILLER',
    gender: 'Male',
    dob: '1949-01-22',
    address: '2842 E 21st Street Kansas City, MO 64145 USA',
    status: 'Single',
    phone: '8169729987'
  }
];
export const ALLERGY_DATA: any[] = [
  {
    dName: 'Data Source-1',
    id: 'M1452481',
    status: 'active',
    code: '3139-3',
    display: 'Pollen',
    patient: 'AADEN SCOTT'
  },
  {
    dName: 'Data Source-2',
    id: 'M1452481',
    status: 'active',
    code: '3139-3',
    display: 'Addreall',
    patient: 'AALIYAH WILSON'
  },
  {
    dName: 'Data Source-3',
    id: 'M1452481',
    status: 'active',
    code: '3139-3',
    display: 'Codeine',
    patient: 'LISA SCOTT'
  },
  {
    dName: 'Data Source-4',
    id: 'M1452481',
    status: 'active',
    code: '3139-3',
    display: 'Pollen',
    patient: 'AARAN SCOTT'
  }
];
export const CONDITION_DATA: any[] = [
  {
    dName: 'Data Source-1',
    id: 'M1452481',
    status: 'active',
    code: '3139-3',
    display: 'Pollen',
    patient: 'AARAN WILSON'
  },
  {
    dName: 'Data Source-2',
    id: 'M1452481',
    status: 'active',
    code: '3139-3',
    display: 'Addreall',
    patient: 'AADEN SCOTT'
  },
  {
    dName: 'Data Source-3',
    id: 'M1452481',
    status: 'active',
    code: '3139-3',
    display: 'Codeine',
    patient: 'AALIYAH MILLER'
  },
  {
    dName: 'Data Source-4',
    id: 'M1452481',
    status: 'active',
    code: '3139-3',
    display: 'Pollen',
    patient: 'LISA SCOTT'
  }
];
export const MEDSTMT_DATA: any[] = [
  {
    dName: 'Data Source-1',
    id: 'M1452481',
    status: 'active',
    med: 'Prednisone',
    patient: 'AADEN SCOTT'
  },
  {
    dName: 'Data Source-2',
    id: 'M1452481',
    status: 'active',
    med: 'Prednisone',
    patient: 'AARAN MILLER'
  },
  {
    dName: 'Data Source-3',
    id: 'M1452481',
    status: 'active',
    med: 'Prednisone',
    patient: 'ALLIYAH SCOTT'
  },
  {
    dName: 'Data Source-4',
    id: 'M1452481',
    status: 'active',
    med: 'Prednisone',
    patient: 'AARAN WILSON'
  }
];
export const OBS_DATA: any[] = [
  {
    dName: 'Data Source-1',
    id: 'M1452481',
    cat: 'social-history',
    code: '3139-3',
    display: 'Weight Dosing',
    val: '84.5kg',
    patient: 'LISA SCOTT'
  },
  {
    dName: 'Data Source-2',
    id: 'M1452481',
    cat: 'social-history',
    code: '3139-3',
    display: 'Weight Dosing',
    val: '84.5kg',
    patient: 'AADEN SCOTT'
  },
  {
    dName: 'Data Source-3',
    id: 'M1452481',
    cat: 'social-history',
    code: '3139-3',
    display: 'Weight Dosing',
    val: '84.5kg',
    patient: 'AARAN WILSON'
  },
  {
    dName: 'Data Source-4',
    id: 'M1452481',
    cat: 'social-history',
    code: '3139-3',
    display: 'Weight Dosing',
    val: '84.5kg',
    patient: 'AALIYAH WILSON'
  }
];
export const TASKS_DATA: any[] = [
  {
    dsName: 'dataset-1',
    sdate: '04-07-2018',
    edate: '05-07-2018',
    status: 'pending',
    target: 'epic.com/FHIR/api/FHIR',
    notes: 'error in data'
  },
  {
    dsName: 'dataset-2',
    sdate: '04-07-2018',
    edate: '05-07-2018',
    status: 'Complete',
    target: 'epic.com/FHIR/api/FHIR',
    notes: 'error in data'
  },
  {
    dsName: 'dataset-3',
    sdate: '04-07-2018',
    edate: '05-07-2018',
    status: 'In Progress',
    target: 'epic.com/FHIR/api/FHIR',
    notes: 'error in data'
  },
  {
    dsName: 'dataset-4',
    sdate: '04-07-2018',
    edate: '05-07-2018',
    status: 'pending',
    target: 'epic.com/FHIR/api/FHIR',
    notes: 'error in data'
  }
];
export const DASHBOARD_DATA: any[] = [
  {
    dsName: 'dataset-1',
    user: 'AARAN MILLER',
    tar: 'epic.com/FHIR/api/FHIR',
    record: '10',
    status: 'In Progress'
  },
  {
    dsName: 'dataset-2',
    user: 'AADEN SCOTT',
    tar: 'cerner.com/FHIR',
    record: '30',
    status: 'Completed'
  },
  {
    dsName: 'dataset-3',
    user: 'AALIYAH WILSON',
    tar: 'open-ic/epic.com/FHIR',
    record: '10',
    status: 'In Progress'
  },
  {
    dsName: 'dataset-4',
    user: 'LISA MILLER',
    tar: 'sandboxcerner.com/FHIR',
    record: '10',
    status: 'Completed'
  }
];
export const Interopx_DATA: any[] = [
  {
    group: '650 of 8590',
    Id: 'IX-15864',
    dob: '14-04-1985 00:00:00',
    fName: 'AARAN',
    lname: 'MILLER',
    mname: 'L',
    gender: 'Male',
    phone: '1457896321'
  }
];
export const Conflict_DATA: any[] = [
  {
    action: 'Linked',
    SId: '864',
    birth: '14-04-1955 00:00:00',
    fName: 'AARAN',
    lname: 'M',
    mname: 'Loreal',
    gender: 'Male',
    phone: '+1457632189',

  }, {
    action: 'Duplicate',
    SId: '98864',
    birth: '04-04-1965 00:00:00',
    fName: 'A',
    lname: 'MILLER',
    mname: 'L',
    gender: 'Male',
    phone: '+1789632145',

  }, {
    action: 'Excluded',
    SId: '6548',
    birth: '14-04-1955 00:00:00',
    fName: 'AARAN',
    lname: 'MILLER',
    mname: 'E',
    gender: 'Male',
    phone: '+5789632114',

  }, {
    action: 'Linked',
    SId: '6589',
    birth: '19-12-1975 00:00:00',
    fName: 'AARAN',
    lname: 'MILLER',
    mname: 'K',
    gender: 'Male',
    phone: '+1789645321',

  }
];
export const UsrMgmt_DATA: any[] = [
  {

    email: 'ravi@test.com',
    name: 'Ravichandra',
    status: 'Active',
    sysadmin: 'true',
    grpadmin: 'true',


  }, {
    email: 'Nagesh@test.com',
    name: 'Nagesh Bashyam',
    status: 'Active',
    sysadmin: 'true',
    grpadmin: 'true',

  }, {
    email: 'sateesh@test.com',
    name: 'Sateesh Reddy',
    status: 'Active',
    sysadmin: 'true',
    grpadmin: 'true',
  }, {
    email: 'raghu@test.com',
    name: 'Raghunath Umapathi',
    status: 'Active',
    sysadmin: 'true',
    grpadmin: 'true',

  }
];
export const Scoring_DATA: any[] = [
  {
    extractionId: '10008',
    dsId: '864',
    // overall: 'A+',
    score: '97/100',
    issues: '9',


  }, {
    extractionId: '10008',
    dsId: '864',
    // overall: 'A+',
    score: '97/100',
    issues: '9',

  }, {
    extractionId: '10008',
    dsId: '864',
    //overall: 'A+',
    score: '97/100',
    issues: '9',


  }, {
    extractionId: '10008',
    dsId: '864',
    //overall: 'A+',
    score: '97/100',
    issues: '9',

  }
];
export const Quality_DATA: any[] = [
  {
    resources: 'Allergy Intolerance',
    // grade: 'A+',
    issues: '9',
    records: 100,


  }, {
    resources: 'Condition',
    // grade: 'A+',
    issues: '9',
    records: 100,

  }, {
    resources: 'Vital Signs',
    // grade: 'A+',
    issues: '9',
    records: 100,

  }, {
    resources: 'Observation',
    //grade: 'A+',
    issues: '9',
    records: 100,

  }
];
export const DS_DATA: any[] = [
  {
    dsId: '10008',
    dsName: 'TestDatasource',
    score: '87/100',
    issues: '7'


  }, {
    dsId: '10008',
    dsName: 'PCP',
    score: '87/100',
    issues: '7'

  }, {
    dsId: '10008',
    dsName: 'Hospital',
    score: '87/100',
    issues: '7'



  }, {
    dsId: '10008',
    dsName: 'Epic',
    score: '87/100',
    issues: '7'

  }
];
export const DSQuality_DATA: any[] = [
  {
    resources: 'Allergy Intolerance',
    // grade: 'A+',
    issues: '7',
    records: 100,


  }, {
    resources: 'Condition',
    // grade: 'A+',
    issues: '7',
    records: 100,

  }, {
    resources: 'Vital Signs',
    // grade: 'A+',
    issues: '7',
    records: 100,

  }, {
    resources: 'Observation',
    //grade: 'A+',
    issues: '7',
    records: 100,

  }
];
export const grpMgmt_DATA: any[] = [
  {
    grpname: 'maintain Allergies',
    grpowner: 'Ravi(ravi123@gmail.com)'
  },
  {
    grpname: 'maintain condition',
    grpowner: 'mounika(mounika@gmail.com)'
  },
  {
    grpname: 'Test Group',
    grpowner: 'Lisa(Lisa@gmail.com)'
  },
  {
    grpname: 'medication group',
    grpowner: 'Roji(roji@gmail.com)'
  }
];
export const addedUser_Mgmt: any[] = [
  {
    name: 'sateesh',
    email: 'sateesh@test.com'

  },
  {
    name: 'Raghu',
    email: 'raghu@test.com'

  },
  {
    name: 'Vijay',
    email: 'vijay@test.com'

  }

]