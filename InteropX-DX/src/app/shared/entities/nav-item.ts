export interface NavItem {
  displayName: string;
  disabled?: boolean;
  iconName: string;
  faIcon: string;
  route?: string;
  children?: NavItem[];
}

export const NAV_ITEMS: NavItem[] = [
  {
    displayName: 'Dashboard',
    iconName: 'dashboard',
    faIcon: undefined,
    route: 'dashboard'
  },
  {
    displayName: 'Data Extraction',
    iconName: 'import_export',
    faIcon: undefined,
    children: [
      {
        displayName: 'Data Set',
        iconName: 'dns',
        faIcon: undefined,
        route: 'datasets/list'
      },
      {
        displayName: 'Tasks',
        iconName: 'list',
        faIcon: undefined,
        route: 'tasks'
      },
      {
        displayName: 'Repository',
        iconName: undefined,
        faIcon: 'fa fa-database',
        route: 'repository'
      }
    ]
  },
  {
    displayName: 'Analytics',
    iconName: 'timeline',
    faIcon: undefined,
    children: [
      {
        displayName: 'Data Visualization',
        iconName: 'insert_drive_file',
        faIcon: undefined,
        route: 'dataVisualization'
      },
      {
        displayName: 'Reports',
        iconName: 'pie_chart',
        faIcon: undefined,
        route: 'reports'
      },
      {
        displayName: 'Data Quality Scoring',
        iconName: 'assessment',
        faIcon: undefined,
        route: 'dataQuality'
      }
      /*  {
          displayName: 'DataQuality By Datasource',
          iconName: undefined,
          faIcon: 'fa fa-sort-numeric-asc',
          route: 'dataQualityByDS'
        }*/
    ]
  },
  {
    displayName: 'PatientMatching',
    iconName: 'contacts',
    faIcon: undefined,
    route: 'matching'
  },
  {
    displayName: 'Administration',
    iconName: 'settings',
    faIcon: undefined,
    children: [
      {
        displayName: 'Data Sources',
        iconName: 'storage',
        faIcon: undefined,
        route: 'datasources'
      },
      {
        displayName: 'Data Sets',
        iconName: undefined,
        faIcon: 'fa fa-clone',
        route: 'datasets'
      },
      {
        displayName: 'Patient Panels',
        iconName: 'people',
        faIcon: undefined,
        route: 'patient-panel'
      },
      {
        displayName: 'User Management',
        iconName: 'account_circle',
        faIcon: undefined,
        route: 'userManagement'
        /*children: [
          {
            displayName: 'Home',
            iconName: 'home',
            faIcon: undefined,
            route: 'home'
          },
          {
            displayName: 'Organization',
            iconName: 'location_city',
            faIcon: undefined,
            route: 'organization'
          },
          {
            displayName: 'user Management',
            iconName: 'perm_identity',
            faIcon: undefined,
            route: 'userManagement'
          },
          {
            displayName: 'Group Management',
            iconName: 'group',
            faIcon: undefined,
            route: 'grpManagement'
          },
          {
            displayName: 'Assign Permissions to Groups',
            iconName: 'playlist_add_check',
            faIcon: undefined,
            route: 'assignperm'
          },
          {
            displayName: 'Permissions',
            iconName: 'verified_user',
            faIcon: undefined,
            route: 'permissions'
          },

        ]*/
      },
      {
        displayName: 'Group Management',
        iconName: 'group',
        faIcon: undefined,
        route: 'groupMgmt'
      }
    ]
  }
];
