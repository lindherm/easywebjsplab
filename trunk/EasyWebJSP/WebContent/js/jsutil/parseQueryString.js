/* parseQueryString.js - a function to parse and decode query strings
 *
 * The author of this program, Safalra (Stephen Morley), irrevocably releases
 * all rights to this program, with the intention of it becoming part of the
 * public domain. Because this program is released into the public domain, it
 * comes with no warranty either expressed or implied, to the extent permitted
 * by law.
 *
 * For more public domain JavaScript code by the same author, visit:
 * http://www.safalra.com/web-design/javascript/
 */


/* Parses a query string and returns an object containing the parsed data. A
 * custom query string can supplied, or the query string from the current
 * document's URL can be used. The return value is an object whose property
 * names and values correspond to the decoded query data. Because a single key
 * may occur multiple times in a query string, the properties of the returned
 * object consist of arrays of values. The parameter is:
 *
 * queryString - the queryString to parse. This parameter is optional, and if it
 *               is not supplied then the query string from the current
 *               document's URL is used. The query string may start with a
 *               question mark, spaces may be encoded either as plus signs or
 *               the escape sequence '%20', and both ampersands and semicolons
 *               are permitted as separators.
 */
function parseQueryString(queryString){

  // define an object to contain the parsed query data
  var result = {};

  // if a query string wasn't specified, use the query string from the URI
  if (queryString == undefined){
    queryString = location.search ? location.search : '';
  }

  // remove the leading question mark from the query string if it is present
  if (queryString.charAt(0) == '?') queryString = queryString.substring(1);

  // replace plus signs in the query string with spaces
  queryString = queryString.replace(/\+/g, ' ');

  // split the query string around ampersands and semicolons
  var queryComponents = queryString.split(/[&;]/g);

  // loop over the query string components
  for (var i = 0; i < queryComponents.length; i++){

    // extract this component's key-value pair
    var keyValuePair = queryComponents[i].split('=');
    var key = decodeURIComponent(keyValuePair[0]);
    var value = decodeURIComponent(keyValuePair[1]);

    // update the parsed query data with this component's key-value pair
    if (!result[key]) result[key] = [];
    result[key].push((keyValuePair.length == 1) ? '' : value);
  }

  // return the parsed query data
  return result;

}
