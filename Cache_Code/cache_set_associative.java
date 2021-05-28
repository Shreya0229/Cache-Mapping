	import java.io.BufferedReader;
	import java.io.IOException;
	import java.io.InputStream;
	import java.io.InputStreamReader;
import java.util.Random;
import java.util.StringTokenizer;

	public class cache_set_associative {
		
		public static int log2(int x) {
			return (int)(Math.log(x)/ Math.log(2));
		}
		
		
		public static String stringwithbits(long tag, int noofbits) {
			String str= Long.toString(tag);
			long zeros_needed= noofbits-str.length();
			
			for(int i=0; i<zeros_needed; i++) {
				str= "0"+str;
			}
			return str;
		}
		
		
		public static long[] tobinary(long ad) {
			String bin = Long.toBinaryString(ad);
			
			String[] temp= bin.split("(?!^)");
			
			long[] arr= new long[bin.length()];
			for(int i=0; i<bin.length(); i++) {
				arr[i]=Long.parseLong(temp[i]);
			}
			
			long[] arrfinal= new long[16];
			for(int i=1; i<=arr.length; i++) {
				arrfinal[16-i]= arr[arr.length-i];
			}
			
			return arrfinal;
		}
		
		
		
		
		

		public static void main(String[] args) throws IOException {
			Readera.init(System.in);
			
			System.out.println("THE SET ASSOCIATIVE CACHE");
			System.out.println();
			
			System.out.println("Enter no.of cache line: ");
			int cache_line = Readera.nextInt();
			
			System.out.println("Enter block size");
			int block_size= Readera.nextInt();
			
			System.out.println("Enter the -->k in k-way associativity: ");
			int k= Readera.nextInt();
			
			
			
			int offset= log2(block_size);
			int index_bit= log2(cache_line/k);
			int k_bit= log2(k);
		

			long[] tag_array= new long[cache_line];
			for(int i=0; i<tag_array.length; i++) {
				tag_array[i]= -5;
			}
			
			
			long[][] data_array= new long[cache_line][ block_size];
			for(int i=0; i<cache_line; i++) {
				for(int j=0; j<block_size; j++) {
					data_array[i][j]=-5;
				}
			}
			
			
			
			int y=0;
			do {
			System.out.println("Enter read or write: ");
			String input= Readera.next();
			
			
			
			if(input.equals("read")) {
				System.out.println("Address to be read: ");
				long address= Readera.nextLong();
				long[] add_in_binary= tobinary(address);
				
		

				
				long[] offset_array= new long[offset];
				for(int i=0; i<offset;i++) {
					offset_array[offset-1-i]= add_in_binary[add_in_binary.length-1-i];
				}
				
				long offset_num=0;
				for(int i=0; i<offset_array.length; i++) {
					offset_num+= Math.pow(10,i)* offset_array[offset_array.length-1-i];
				}
				String s= Integer.toString((int)offset_num);
				int offset_decimal= Integer.parseInt(s, 2);
				
				
				
				
				
				long[] index_array= new long[index_bit];
				for(int i=0; i<index_bit;i++) {
					index_array[index_bit-1-i]= add_in_binary[add_in_binary.length-1-offset-i];
				}
				
				long index_num=0;
				for(int i=0; i<index_array.length; i++) {
					index_num+= Math.pow(10,i)* index_array[index_array.length-1-i];
				}
				String ind= Integer.toString((int)index_num);
				int index_decimal= Integer.parseInt(ind, 2);
				
				
				
				
				long[] tag_part= new long[16-offset-index_bit];
				for(int i=0; i<16-offset-index_bit;i++) {
					tag_part[tag_part.length-1-i]=add_in_binary[add_in_binary.length-offset-index_bit-1-i];
				}
				
				long tag_num=0;
				for(int i=0; i<tag_part.length; i++) {
					tag_num+= Math.pow(10,i)* tag_part[tag_part.length-1-i];
				}
				
				
				
				
				int tag_foundat=-1;
				for(int a=0; a<k; a++) {
					if(tag_num==tag_array[(index_decimal*k)+a]) {
						tag_foundat=(index_decimal*k)+a;
						break;
					}		
				}
				
				
				
				if(tag_foundat!=-1&& data_array[tag_foundat][offset_decimal]!=-5 ) {
					System.out.println("Cache hit");
					System.out.print("the data at that address is: "+ "");
					System.out.print(""+data_array[tag_foundat][offset_decimal]);
				}
				
				else if(tag_foundat!=-1&& data_array[tag_foundat][offset_decimal]==-5 ) {
					System.out.println("Cache hit");
					System.out.println("Data not present at the address yet");
				}
				else {System.out.println("Cache miss");
					  System.out.println("Address not found");
				}
				
				
				System.out.println();
				System.out.println("the content of cache are: ");
				for(int i=0; i<cache_line; i++) {
					
					
					
					if(tag_array[i]!=-5) {
					System.out.println("Line number "+ (i+1)+" ");
					String tag_str= stringwithbits(tag_array[i], 16-offset-index_bit);
					
					
					String ind_inbinary= Long.toBinaryString((int)(i/k));
					long le= Long.parseLong(ind_inbinary);
					String indpart_str= stringwithbits(le, index_bit);
					
					for(int j=0; j<block_size; j++) {
						String data_inbinary= Long.toBinaryString(j);
						long lu= Long.parseLong(data_inbinary);
						String datapart_str= stringwithbits(lu, offset);
						
						
						
						long final_decimal_address= Long.parseLong(tag_str+ indpart_str+ datapart_str,2);
						
						if(data_array[i][j]!=-5)
							System.out.println("		Address: "+final_decimal_address+" "+ "Value: "+data_array[i][j]);
						else
							System.out.println("		Address: "+final_decimal_address+" "+ "Value: null");
					 }
					}
					
					
					else {
						System.out.println("Line number "+(i+1)+ " "); 
						System.out.println("		No address found" );
						
					}
					}
				
				
				
			}
				
			
			
			
			
			
			
			
			
			
			
			if(input.equals("write")) {
				System.out.println("Address to be written at: ");
				long address= Readera.nextLong();
				System.out.println("Enter data value: ");
				long value = Readera.nextLong();
				long[] add_in_binary= tobinary(address);
				
				
				
				
				long[] offset_array= new long[offset];
				for(int i=0; i<offset;i++) {
					offset_array[offset-1-i]= add_in_binary[add_in_binary.length-1-i];
				}
				
				long offset_num=0;
				for(int i=0; i<offset_array.length; i++) {
					offset_num+= Math.pow(10,i)* offset_array[offset_array.length-1-i];
				}
				String s= Integer.toString((int)offset_num);
				int offset_decimal= Integer.parseInt(s, 2);
				
				
				
				
				
				long[] index_array= new long[index_bit];
				for(int i=0; i<index_bit;i++) {
					index_array[index_bit-1-i]= add_in_binary[add_in_binary.length-1-offset-i];
				}
				
				long index_num=0;
				for(int i=0; i<index_array.length; i++) {
					index_num+= Math.pow(10,i)* index_array[index_array.length-1-i];
				}
				String ind= Integer.toString((int)index_num);
				int index_decimal= Integer.parseInt(ind, 2);
				
				
				
				long[] tag_part= new long[16-offset-index_bit];
				for(int i=0; i<16-offset-index_bit;i++) {
					tag_part[tag_part.length-1-i]=add_in_binary[add_in_binary.length-offset-index_bit-1-i];
				}
				
				long tag_num=0;
				for(int i=0; i<tag_part.length; i++) {
					tag_num+= Math.pow(10,i)* tag_part[tag_part.length-1-i];
				}
				
				
				
				int tag_foundat=-1;
				
				
				for(int a=0; a<k; a++) {
					if(tag_num==tag_array[(index_decimal*k)+a]) {
						tag_foundat=(index_decimal*k)+a;
						data_array[tag_foundat][offset_decimal]= value;
						System.out.println("Cache hit");
						System.out.println("Value updated");
						break;
					}		
				}
				
				
				if(tag_foundat==-1) {
					int flag=-10;
					
					for(int a=0; a<k; a++) {
						if(tag_array[(index_decimal*k)+a]==-5) {
							tag_array[(index_decimal*k)+a]=tag_num;
							data_array[(index_decimal*k)+a][offset_decimal]= value;
							System.out.println("Cache miss");
							System.out.println("Value added in cache");
							flag=0;
							break;
						}
					}
					
					
					
					if(flag==-10) {
						Random rand= new Random();
						int rand_int= rand.nextInt(k);
						long replaced_tag= tag_array[index_decimal*k+rand_int];
						
						
						for(int i=0; i<block_size; i++) {
							data_array[index_decimal*k+rand_int][i]=-5;
						}
						tag_array[index_decimal*k+rand_int]=tag_num;
						data_array[index_decimal*k+rand_int][offset_decimal]= value;
						
						System.out.println("Cache miss");
						String nn= stringwithbits(replaced_tag,16-offset);
						String no= stringwithbits(tag_num,16-offset);
						System.out.println("the tag address "+nn+" was replaced by our new tag "+no);
						System.out.println("Value added in cache");
					}
					
				   }
				
				
				
				
				
				System.out.println();
				System.out.println("the content of cache are: ");
				for(int i=0; i<cache_line; i++) {
					
					
					
					if(tag_array[i]!=-5) {
					System.out.println("Line number "+ (i+1)+" ");
					String tag_str= stringwithbits(tag_array[i], 16-offset-index_bit);
					
					
					String ind_inbinary= Long.toBinaryString((int)(i/k));
					long le= Long.parseLong(ind_inbinary);
					String indpart_str= stringwithbits(le, index_bit);
					
					for(int j=0; j<block_size; j++) {
						String data_inbinary= Long.toBinaryString(j);
						long lu= Long.parseLong(data_inbinary);
						String datapart_str= stringwithbits(lu, offset);
						
						
						
						long final_decimal_address= Long.parseLong(tag_str+ indpart_str+ datapart_str,2);
						
						if(data_array[i][j]!=-5)
							System.out.println("		Address: "+final_decimal_address+" "+ "Value: "+data_array[i][j]);
						else
							System.out.println("		Address: "+final_decimal_address+" "+ "Value: null");
					 }
					}
					
					
					else {
						System.out.println("Line number "+(i+1)+ " "); 
						System.out.println("		No address found" );
						
					}
					}
				
				
				
				
				
				
			}
			System.out.println();
			System.out.println("To enter more press--> 1 or to terminate --> 0");
			y= Readera.nextInt();
			
			} while(y==1);
		} 

	}



	class Readera {
	    static BufferedReader reader;
	    static StringTokenizer tokenizer;
	    static void init(InputStream input) {
	        reader = new BufferedReader(
	                     new InputStreamReader(input) );
	        tokenizer = new StringTokenizer("");
	    }

	    static String next() throws IOException {
	        while ( ! tokenizer.hasMoreTokens() ) {

	        	tokenizer = new StringTokenizer(
	                   reader.readLine() );
	        }
	        return tokenizer.nextToken();
	    }

	    static int nextInt() throws IOException {
	        return Integer.parseInt( next() );
	    }
		
	    static double nextDouble() throws IOException {
	        return Double.parseDouble( next() );
	    }
	    static long nextLong() throws IOException {
	        return Long.parseLong( next() );
	    }
	}
	    