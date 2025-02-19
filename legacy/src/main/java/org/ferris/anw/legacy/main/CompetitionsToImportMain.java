package org.ferris.anw.legacy.main;

/**
 *
 * @author Michael
 */
public class CompetitionsToImportMain {

//    Date now = Date.valueOf(LocalDate.now());
//    
//    public static void main(String[] args) throws Exception {
//        new CompetitionsToImportMain().go();
//    }
//
//    public void go() throws Exception 
//    {
//        System.out.printf("Starting CompetitionsToImportMain%n%n");
//        
//        File file = new File("../competitions-to-import.txt");
//        LineNumberReader reader 
//            = new LineNumberReader(new FileReader(file));
//        
//        
//        List<Competition> comps = new LinkedList<>();
//        for (String line=reader.readLine(); line != null; line=reader.readLine()) {
//            if (line.startsWith("#")) {
//                continue;
//            }
//            if (line.isBlank()) {
//                continue;
//            }
//            String[] tokens = line.split("\\t",-1);
//            String gymName = tokens[0];
//            String beginDate = tokens[1];
//            String endDate = tokens[2];
//            String type = tokens[3];
//            String league = tokens[4];
//            String isAttendancePlanned = tokens[5];
//            String isRegistered = tokens[6];
//            comps.add(
//                new Competition(gymName, beginDate, endDate, type, league, isAttendancePlanned, isRegistered)
//            );
//        }
//        
//        conn = DriverManager.getConnection(databaseURL);
//        
//        find(comps);
//        update(comps.stream().filter(c -> c.competitionId != null).collect(Collectors.toList()));
//        insert(comps.stream().filter(c -> c.competitionId == null).collect(Collectors.toList()));        
//        
//        System.out.printf("%n%nStopping CompetitionsToImportMain%n");
//    }
//    
//    private void update(List<Competition> comps) {
//        System.out.printf("FOUND: %d%n", comps.size());
//        for (Competition comp : comps) {
//            try (PreparedStatement stmt = createUpdateStatement(comp);) 
//            {
//                int i = stmt.executeUpdate();
//                if (i == 0) {
//                    throw new RuntimeException(
//                        String.format("Failed to update! %s",comp)
//                    );
//                }
//            } catch (Exception e) {
//                throw new RuntimeException(e);
//            }
//        }
//    }
//
//    private void insert(List<Competition> comps) {
//        System.out.printf("NEW: %d%n", comps.size());
//    }
//    
//    private void find(List<Competition> comps) throws Exception {
//        for (Competition comp : comps) {
//            try (PreparedStatement stmt = createSelectStatement(comp); 
//                 ResultSet rs = stmt.executeQuery()) 
//            {
//                if (rs.next()) 
//                {
//                    // get the ID of the matching row in the database
//                    comp.competitionId = rs.getLong("ID");
//                    
//                    // Check if there is more than one match in the DB, which there shouldn't be.
//                    if (rs.next()) {
//                        throw new RuntimeException(
//                            String.format("Duplicate! %s",comp)
//                        );
//                    }
//                } 
//            } catch (Exception e) {
//                throw new RuntimeException(e);
//            }
//        }
//    }
//    
//    private PreparedStatement createUpdateStatement(Competition comp) 
//    throws Exception {
//        PreparedStatement stmt
//            = conn.prepareStatement(
//                " update competitions set "
//                + "   end_date = ? "
//                + " , last_found_on_date = ? "
//                + " where "
//                + "  id = ? "
//            );
//        
//        // end_date
//        if (comp.endDate == null) {
//            stmt.setNull(1, Types.DATE);
//        } else {
//            stmt.setDate(1, comp.endDate);
//        }
//        
//        // last_found_on_date
//        stmt.setDate(2, now);
//        
//        // ID
//        stmt.setLong(3, comp.competitionId);
//        
//        return stmt;
//    }
//    
//    private PreparedStatement createSelectStatement(Competition comp) 
//    throws Exception {
//        PreparedStatement stmt
//            = conn.prepareStatement(
//                " select "
//                + "  ID "
//                + " from "
//                + "  competitions "
//                + " where "
//                + "  gym_name = ? "
//                + "  and "
//                + "  begin_date = ? "
//                + "  and "
//                + "  league = ? "
//                + "  and "
//                + "  type = ? "
//            );
//        stmt.setString(1, comp.gymName);
//        stmt.setDate(2, comp.beginDate);
//        stmt.setString(3, comp.league);
//        stmt.setString(4, comp.type);
//        
//        return stmt;
//    }
}
