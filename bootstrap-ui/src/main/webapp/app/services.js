"use strict";

var services = angular.module('smlBootstrap.services', ['ngResource']);

var dontBlockOnAjaxHeader = { "dontBlockPageOnAjax": "true" };
var nonArrayGetWithoutBlockOnAjax = { method: "GET", isArray: false, headers: dontBlockOnAjaxHeader };



services.factory('MemberService', function ($resource) {

    var self = this;

    self.Resource = $resource('rest/slurp/:id', { }, {
        insert: { method: "PUT"},
        query: nonArrayGetWithoutBlockOnAjax,
        slurpMembers: { method: "POST", isArray: true, headers: dontBlockOnAjaxHeader }
    });

    var memberService = {};

    memberService.slurpMembers = function (url, successFunction) {
        self.Resource.slurpMembers({ url: url }, successFunction);
    };

    return memberService;
});

services.factory("FlashService", function () {

    var queue = [];

    return {
        set: function (message) {
            queue.push(message);
        },
        get: function () {
            return queue.shift();
        }
    };
});
