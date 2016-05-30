package org.goeuro
/*
	This is the Geb configuration file.
	See: http://www.gebish.org/manual/current/configuration.html
*/


waiting {
    presets {
        slow {
            timeout = 60
            retryInterval = 1
        }
        quick {
            timeout = 10
        }
    }
}
cacheDriverPerThread = false
atCheckWaiting = true
cacheDriver = false

