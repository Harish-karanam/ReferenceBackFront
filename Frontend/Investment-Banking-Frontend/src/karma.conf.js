// frontend/karma.conf.js
module.exports = function (config) {
  config.set({
    basePath: '',
    frameworks: ['jasmine', '@angular-devkit/build-angular'],
    
    plugins: [
      require('karma-jasmine'),
      require('karma-chrome-launcher'),
      require('karma-jasmine-html-reporter'),
      require('karma-coverage'),
      require('@angular-devkit/build-angular/plugins/karma')
    ],
    
    client: {
      jasmine: {
        // Configure jasmine options
        random: false,  // Tests run in same order
        failFast: false,
        timeoutInterval: 10000
      },
      clearContext: false // leave Jasmine Spec Runner output visible in browser
    },
    
    jasmineHtmlReporter: {
      suppressAll: true // removes the duplicated traces
    },
    
    coverageReporter: {
      dir: require('path').join(__dirname, './coverage'),
      subdir: '.',
      reporters: [
        { type: 'html' },
        { type: 'text-summary' },
        { type: 'lcovonly' },
        { type: 'json-summary' }
      ],
      check: {
        global: {
          statements: 75,    // Minimum 75% statement coverage
          branches: 70,      // Minimum 70% branch coverage
          functions: 75,     // Minimum 75% function coverage
          lines: 75          // Minimum 75% line coverage
        }
      },
      watermarks: {
        statements: [75, 90],
        branches: [70, 85],
        functions: [75, 90],
        lines: [75, 90]
      }
    },
    
    reporters: ['progress', 'kjhtml', 'coverage'],
    
    files: [
      { pattern: 'src/test.ts', watched: true },
      { pattern: 'src/**/*.spec.ts', watched: true }
    ],
    
    preprocessors: {
      'src/**/*.ts': ['coverage']
    },
    
    port: 9876,
    colors: true,
    logLevel: config.LOG_INFO,
    autoWatch: true,
    browsers: ['Chrome'],
    
    customLaunchers: {
      ChromeHeadlessCI: {
        base: 'ChromeHeadless',
        flags: [
          '--no-sandbox',
          '--disable-gpu',
          '--disable-dev-shm-usage',
          '--disable-web-security',
          '--remote-debugging-port=9222'
        ]
      },
      ChromeDebug: {
        base: 'Chrome',
        flags: ['--remote-debugging-port=9222']
      }
    },
    
    singleRun: false,
    restartOnFileChange: true,
    
    // Increase timeout for slow tests
    browserNoActivityTimeout: 30000,
    browserDisconnectTimeout: 10000,
    browserDisconnectTolerance: 3,
    captureTimeout: 60000
  });
};