package jmagine

import grails.gorm.transactions.Transactional

@Transactional
class RightsService {

    def simpleRoleCheck(User user, String authority ) {
        if( user && authority ) {
            Role role = Role.findByAuthority( authority )
            if( role ) {
                Role role_admin = Role.findByAuthority( 'ROLE_ADMIN' )
                Role role_op = Role.findByAuthority( 'ROLE_OP' )

                if( authority == 'ROLE_OP' ) {
                    def roles = UserRole.findAllByUser( user )
                    if( roles.role.contains( role )) return true
                    else return false
                }
                else if( authority == 'ROLE_ADMIN' ) {
                    def roles = UserRole.findAllByUser( user )
                    if( roles.role.contains( role_admin ) || roles.role.contains( role_op ) ) return true
                    else return false
                }
                else if( authority == 'ROLE_MOD' ) {
                    def roles = UserRole.findAllByUser( user )
                    if( roles.role.contains( role_admin ) || roles.role.contains( role_op ) || roles.role.contains( role )) return true
                    else return false
                }
            }
        }
        return false
    }

    def hasHigherRole(User userA, User userB ) {
        Role roleA = userA.getAuthorities()[0]
        Role roleB = userB.getAuthorities()[0]

        if( roleB.authority == 'ROLE_OP' ) {
            if( roleA.level == roleB.level ) return true;
            else return false;
        }
        else if( roleA.level > roleB.level ) return true
        else return false
    }
}
