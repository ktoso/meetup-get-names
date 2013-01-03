"use strict";

var controllers = angular.module('smlBootstrap.controllers', ['smlBootstrap.services']);

controllers.controller('MemberController', function MemberController($scope, $timeout, $window, MemberService) {

    var self = this;

    $scope.meetupUrl = "";
    $scope.members = []

    $scope.slurpFromMeetup = function() {
      console.log("Will slurp members from:", $scope.meetupUrl);

      MemberService.slurpMembers($scope.meetupUrl, function (response) {
          console.log(response);
          $scope.members = response;
      });
    }

});

