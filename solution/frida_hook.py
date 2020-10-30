import frida, sys

def on_message(message, data):
    if message['type'] == 'send':
        print("[*] {0}".format(message['payload']))
    else:
        print(message)

jscode = """
Java.perform(function () {
  // Function to hook is defined here
  var HttpRequestor = Java.use('com.novuslabs.androidrechallenge.HttpRequestor');

  // Whenever Async Task is called
  var doInBackground = HttpRequestor.doInBackground;
  doInBackground.overload('[Ljava.lang.String;').implementation = function (str) {
    // Show a message to know that the function got called
    send('Async Task Executed');
    console.log(str[0]);
    // Call the original function handler
    var JavaString = Java.use('java.lang.String');
    str[0] = JavaString.$new("http://www.google.com");
    console.log(str[0]);
    return doInBackground.call(this, str);
    console.log('Done');
  };
});
"""

process = frida.get_usb_device().attach('com.novuslabs.androidrechallenge')
script = process.create_script(jscode)
script.on('message', on_message)
print('[*] Running CTF')
script.load()
sys.stdin.read()